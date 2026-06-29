package vs.fitnessOnline.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final KorisnikRepository korisnikRepository;
	private final AdministratorRepository administratorRepository;
	private final SavjetnikRepository savjetnikRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	@Value("${application.auth.admin.name}")
	private String adminName;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String korisnickoIme = authentication.getName();
		String lozinka = authentication.getCredentials().toString();
		if (adminName.equals(korisnickoIme)) {
			Administrator admin = administratorRepository.findByKorisnickoIme(korisnickoIme)
					.orElseThrow(() -> new EntitetNijePronadjenException("Administrator nije pronađen"));
			if (!passwordEncoder.matches(lozinka, admin.getLozinka())) {
				throw new NeispravniKredencijaliException("Neispravna lozinka");
			}
			var sigurniAdministrator = modelMapper.map(admin, SigurniKorisnik.class);
			return new UsernamePasswordAuthenticationToken(sigurniAdministrator, null,
					sigurniAdministrator.getAuthorities());
		} else if (savjetnikRepository.findAll().stream().map(savjetnik -> savjetnik.getKorisnickoIme())
				.anyMatch(imeSavjetnika -> imeSavjetnika.equals(korisnickoIme))) {
			Savjetnik savjetnik = savjetnikRepository.findByKorisnickoIme(korisnickoIme)
					.orElseThrow(() -> new EntitetNijePronadjenException("Savjetnik nije pronađen"));
			if (!passwordEncoder.matches(lozinka, savjetnik.getLozinka())) {
				throw new NeispravniKredencijaliException("Neispravna lozinka");
			}
			var sigurniSavjetnik = modelMapper.map(savjetnik, SigurniKorisnik.class);
			return new UsernamePasswordAuthenticationToken(sigurniSavjetnik, null, sigurniSavjetnik.getAuthorities());
		} else {
			Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
					.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
			if (!passwordEncoder.matches(lozinka, korisnik.getLozinka())) {
				throw new NeispravniKredencijaliException("Neispravna lozinka");
			}
			var sigurniKorisnik = modelMapper.map(korisnik, SigurniKorisnik.class);
			return new UsernamePasswordAuthenticationToken(sigurniKorisnik, null, sigurniKorisnik.getAuthorities());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
