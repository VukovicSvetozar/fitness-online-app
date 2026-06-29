package vs.fitnessOnline.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;

@Service
@RequiredArgsConstructor
public class KorisnikService {

	private final KorisnikRepository korisnikRepository;
	private final ModelMapper modelMapper;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	public KorisnikInfo vratiPodatkeKorisnika(String korisnickoIme) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		KorisnikInfo korisnikInfo = modelMapper.map(korisnik, KorisnikInfo.class);
		korisnikInfo.setStaraLozinka("");
		korisnikInfo.setNovaLozinka("");
		return korisnikInfo;
	}

	public void azurirajPodatkeKorisnika(KorisnikInfo zahtjev) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		if (!(zahtjev.getStaraLozinka().isBlank() || zahtjev.getNovaLozinka().isBlank())) {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(zahtjev.getKorisnickoIme(), zahtjev.getStaraLozinka()));
			korisnik.setLozinka(passwordEncoder.encode(zahtjev.getNovaLozinka()));
		}
		korisnik.setIme(zahtjev.getIme());
		korisnik.setPrezime(zahtjev.getPrezime());
		korisnik.setGrad(zahtjev.getGrad());
		korisnik.setEmail(zahtjev.getEmail());
		korisnik.setBrojTelefona(zahtjev.getBrojTelefona());
		korisnik.setAvatarUrl(zahtjev.getAvatarUrl());
		korisnikRepository.saveAndFlush(korisnik);
	}

}
