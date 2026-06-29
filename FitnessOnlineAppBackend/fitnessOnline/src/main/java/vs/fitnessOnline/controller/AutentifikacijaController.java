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
import vs.fitnessOnline.dto.AktivacijaZahtjev;
import vs.fitnessOnline.dto.AutentifikacijaSavjetnikOdgovor;
//import vs.fitnessOnline.dto.AutentifikacijaSavjetnikOdgovor;
import vs.fitnessOnline.dto.PrijavaOdgovor;
import vs.fitnessOnline.dto.PrijavaZahtjev;
import vs.fitnessOnline.dto.RegistracijaZahtjev;
import vs.fitnessOnline.service.AutentifikacijaService;

@RestController
@RequestMapping("api/autentifikacija")
@RequiredArgsConstructor
public class AutentifikacijaController {

	private final AutentifikacijaService autentifikacijaService;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/registracija")
	public ResponseEntity<Void> registracija(@RequestBody @Valid RegistracijaZahtjev zahtjev) {
		autentifikacijaService.registracija(zahtjev);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dostupnost/korisnicko-ime/{korisnickoIme}")
	public ResponseEntity<Boolean> provjeriDostupnostKorisnickogImena(@PathVariable @Valid String korisnickoIme) {
		boolean dostupno = autentifikacijaService.provjeriDostupnostKorisnickogImena(korisnickoIme);
		return ResponseEntity.ok(dostupno);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dostupnost/email/{email}")
	public ResponseEntity<Boolean> provjeriDostupnostEmaila(@PathVariable @Valid String email) {
		boolean dostupno = autentifikacijaService.provjeriDostupnostEmaila(email);
		return ResponseEntity.ok(dostupno);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/aktivacija")
	public ResponseEntity<PrijavaOdgovor> aktivacija(@RequestBody @Valid AktivacijaZahtjev zahtjev) {
		PrijavaOdgovor prijavaOdgovor = autentifikacijaService.aktivacija(zahtjev);
		return ResponseEntity.ok(prijavaOdgovor);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/prijava")
	public ResponseEntity<PrijavaOdgovor> prijava(@RequestBody @Valid PrijavaZahtjev zahtjev) {
		PrijavaOdgovor prijavaOdgovor = autentifikacijaService.prijava(zahtjev);
		return ResponseEntity.ok(prijavaOdgovor);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/token-savjetnik")
	public ResponseEntity<AutentifikacijaSavjetnikOdgovor> autentifikacijaSavjetnik(
			@RequestBody @Valid PrijavaZahtjev zahtjev) {
		AutentifikacijaSavjetnikOdgovor odgovor = autentifikacijaService.autentifikacijaSavjetnik(zahtjev);
		return ResponseEntity.ok(odgovor);
	}

}
