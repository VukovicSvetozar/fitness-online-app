package vs.fitnessOnline.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.service.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/porukeKorisnika")
@RequiredArgsConstructor
public class PorukaKorisniciController {

	@Autowired
	private PorukaKorisniciService porukaService;

	@GetMapping("/imena/{korisnickoIme}")
	public ResponseEntity<Set<String>> vratiKorisnickaImena(@PathVariable @Valid String korisnickoIme) {
		Set<String> korisnickaImena = porukaService.vratiKorisnickaImena(korisnickoIme);
		if (korisnickaImena.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(korisnickaImena, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Void> dodajPoruku(@RequestBody PorukaKorisnikZahtjev zahtjev) {
		porukaService.dodajPoruku(zahtjev);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/poruke/{korisnickoImePrviUcesnik}/{korisnickoImeDrugiUcesnik}")
	public ResponseEntity<Set<PorukaKorisnikOdgovor>> vratiPoruke(@PathVariable @Valid String korisnickoImePrviUcesnik,
			@PathVariable @Valid String korisnickoImeDrugiUcesnik) {
		Set<PorukaKorisnikOdgovor> poruke = porukaService.vratiPoruke(korisnickoImePrviUcesnik,
				korisnickoImeDrugiUcesnik);
		if (poruke.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(poruke, HttpStatus.OK);
	}
}