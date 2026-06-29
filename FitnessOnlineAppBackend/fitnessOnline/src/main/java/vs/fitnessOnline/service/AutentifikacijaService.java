package vs.fitnessOnline.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;
import vs.fitnessOnline.security.*;

@Service
@RequiredArgsConstructor
public class AutentifikacijaService {

	private final KorisnikRepository korisnikRepository;
	private final SavjetnikRepository savjetnikRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Value("${application.auth.admin.name}")
	private String adminName;

	public void registracija(RegistracijaZahtjev zahtjev) {
		if (korisnikRepository.existsByKorisnickoIme(zahtjev.getKorisnickoIme()))
			throw new SukobImenaException("Korisnicko ime se vec koristi.");
		if (korisnikRepository.existsByEmail(zahtjev.getEmail()))
			throw new SukobImenaException("Email adresa se vec koristi.");
		Korisnik korisnik = modelMapper.map(zahtjev, Korisnik.class);
		korisnik.setLozinka(passwordEncoder.encode(korisnik.getLozinka()));
		korisnik.setDatumPristupa(LocalDate.now().toString());
		korisnik.setPretplata(0);
		String kljuc = kreirajKljuc();
		korisnik.setKljuc(kljuc);
		korisnik.setUloga(UlogaKorisnik.REGISTROVANI.name());
		if (!mailService.posaljiLinkZaPotvrduRegistracije(zahtjev.getEmail(), zahtjev.getKorisnickoIme(), kljuc)) {
			throw new PogresanZahtjevException("Slanje email-a nije bilo uspjesno.");
		}
		korisnikRepository.saveAndFlush(korisnik);
	}

	public boolean provjeriDostupnostKorisnickogImena(String korisnickoIme) {
		if (adminName.equals(korisnickoIme)
				|| savjetnikRepository.findAll().stream().map(savjetnik -> savjetnik.getKorisnickoIme())
						.anyMatch(imeSavjetnika -> imeSavjetnika.equals(korisnickoIme)))
			return false;
		Optional<Korisnik> korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme);
		return !korisnik.isPresent();
	}

	public boolean provjeriDostupnostEmaila(String email) {
		Optional<Korisnik> korisnik = korisnikRepository.findByEmail(email);
		return !korisnik.isPresent();
	}

	public PrijavaOdgovor aktivacija(AktivacijaZahtjev zahtjev) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		if (!korisnik.getKljuc().equals(String.valueOf(zahtjev.getKljuc())))
			throw new NeispravniKredencijaliException("Pogrešan ključ.");
		if (UlogaKorisnik.VERIFIKOVANI.name().equals(korisnik.getUloga()))
			throw new PogresanZahtjevException("Korisnik je već verifikovan");
		korisnik.setUloga(UlogaKorisnik.VERIFIKOVANI.name());
		korisnikRepository.saveAndFlush(korisnik);
		var sigurniKorisnik = modelMapper.map(korisnik, SigurniKorisnik.class);
		var jwtToken = jwtService.kreirajToken(sigurniKorisnik);
		var refreshJwtTokens = jwtService.kreirajRefreshToken(sigurniKorisnik);
		return PrijavaOdgovor.builder().korisnickoIme(zahtjev.getKorisnickoIme()).avatarUrl(korisnik.getAvatarUrl())
				.jwtToken(jwtToken).refreshJwtToken(refreshJwtTokens).build();
	}

	public PrijavaOdgovor prijava(PrijavaZahtjev zahtjev) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(zahtjev.getKorisnickoIme(), zahtjev.getLozinka()));
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		if (UlogaKorisnik.REGISTROVANI.name().equals(korisnik.getUloga())) {
			String kljuc = kreirajKljuc();
			korisnik.setKljuc(kljuc);
			if (!mailService.posaljiLinkZaPotvrduRegistracije(korisnik.getEmail(), korisnik.getKorisnickoIme(),
					kljuc)) {
				throw new PogresanZahtjevException("Slanje email-a nije bilo uspjesno.");
			}
			korisnikRepository.saveAndFlush(korisnik);
			return null;
		}
		if (UlogaKorisnik.VERIFIKOVANI.name().equals(korisnik.getUloga())) {
			var sigurniKorisnik = modelMapper.map(korisnik, SigurniKorisnik.class);
			var jwtToken = jwtService.kreirajToken(sigurniKorisnik);
			var refreshJwtTokens = jwtService.kreirajRefreshToken(sigurniKorisnik);
			return PrijavaOdgovor.builder().korisnickoIme(korisnik.getKorisnickoIme())
					.avatarUrl(korisnik.getAvatarUrl()).jwtToken(jwtToken).refreshJwtToken(refreshJwtTokens).build();
		}
		throw new PogresanZahtjevException("Došlo je do greške tokom prijave.");
	}

	public AutentifikacijaSavjetnikOdgovor autentifikacijaSavjetnik(PrijavaZahtjev zahtjev) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(zahtjev.getKorisnickoIme(), zahtjev.getLozinka()));
		Savjetnik savjetnik = savjetnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Savjetnik nije pronađen"));
		var sigurniKorisnik = modelMapper.map(savjetnik, SigurniKorisnik.class);
		var jwtToken = jwtService.kreirajToken(sigurniKorisnik);
		return AutentifikacijaSavjetnikOdgovor.builder().jwtToken(jwtToken).build();
	}

	private String kreirajKljuc() {
		String dozvoljeniKarakteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder kljuc = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			int slucajanIndeks = random.nextInt(dozvoljeniKarakteri.length());
			kljuc.append(dozvoljeniKarakteri.charAt(slucajanIndeks));
		}
		return kljuc.toString();
	}

}
