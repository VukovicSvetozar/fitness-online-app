package vs.fitnessOnline.security;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class ApplicationConfiguration {

	private final KorisnikRepository korisnikRepository;
	private final AdministratorRepository administratorRepository;
	private final SavjetnikRepository savjetnikRepository;
	private final ModelMapper modelMapper;

	@Value("${application.auth.admin.name}")
	private String adminName;

	@Bean
	UserDetailsService userDetailsService() {
		return korisnickoIme -> {
			if (adminName.equals(korisnickoIme)) {
				Administrator admin = administratorRepository.findByKorisnickoIme(korisnickoIme).get();
				return modelMapper.map(admin, SigurniKorisnik.class);
			} else if (savjetnikRepository.findAll().stream().map(savjetnik -> savjetnik.getKorisnickoIme())
					.anyMatch(imeSavjetnika -> imeSavjetnika.equals(korisnickoIme))) {
				Savjetnik savjetnik = savjetnikRepository.findByKorisnickoIme(korisnickoIme).get();
				return modelMapper.map(savjetnik, SigurniKorisnik.class);
			} else {
				Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
						.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronadjen."));
				return modelMapper.map(korisnik, SigurniKorisnik.class);
			}
		};
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration AuthenticationConfiguration)
			throws Exception {
		return AuthenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
