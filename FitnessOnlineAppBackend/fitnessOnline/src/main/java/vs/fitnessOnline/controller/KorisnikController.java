package vs.fitnessOnline.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.KorisnikInfo;
import vs.fitnessOnline.service.KorisnikService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/korisnik")
@RequiredArgsConstructor
public class KorisnikController {

	private final KorisnikService korisnikService;

	@GetMapping("/{korisnickoIme}")
	public ResponseEntity<KorisnikInfo> vratiPodatkeKorisnika(@PathVariable @Valid String korisnickoIme) {
		KorisnikInfo korisnikInfo = korisnikService.vratiPodatkeKorisnika(korisnickoIme);
		return ResponseEntity.ok(korisnikInfo);
	}

	@PostMapping("/azuriraj")
	public ResponseEntity<Void> azurirajPodatkeKorisnika(@RequestBody @Valid KorisnikInfo zahtjev) {
		korisnikService.azurirajPodatkeKorisnika(zahtjev);
		return ResponseEntity.ok().build();
	}

}
