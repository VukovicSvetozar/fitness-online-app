package vs.fitnessOnline.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.AktivnostInfo;
import vs.fitnessOnline.dto.AktivnostOdgovor;
import vs.fitnessOnline.dto.AktivnostZahtjev;
import vs.fitnessOnline.dto.MasaOdgovor;
import vs.fitnessOnline.dto.MasaZahtjev;
import vs.fitnessOnline.service.AktivnostService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aktivnosti")
public class AktivnostController {

	private final AktivnostService aktivnostService;

	@PostMapping("")
	public ResponseEntity<Void> kreirajAktivnsot(@RequestBody @Valid AktivnostZahtjev zahtjev) {
		aktivnostService.kreirajAktivnsot(zahtjev);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/{korisnickoIme}/{datum}")
	public ResponseEntity<List<AktivnostOdgovor>> vratiSveAktivnostiPoDatumu(
			@PathVariable(value = "korisnickoIme") @Valid String korisnickoIme,
			@PathVariable(value = "datum") @Valid String datum) {
		List<AktivnostOdgovor> aktivnosti = aktivnostService.vratiSveAktivnostiPoDatumu(korisnickoIme, datum);
		if (aktivnosti.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(aktivnosti, HttpStatus.OK);
	}

	@GetMapping("/dnevnik/{korisnickoIme}")
	public ResponseEntity<String> vratiDnevnikKorisnika(
			@PathVariable(value = "korisnickoIme") @Valid String korisnickoIme) {
		String dnevnik = aktivnostService.vratiDnevnikKorisnika(korisnickoIme);
		return ResponseEntity.ok(dnevnik);
	}

	@GetMapping("/info/{korisnickoIme}")
	public ResponseEntity<Set<AktivnostInfo>> vratiInformacijeOAktivnostimaKorisnika(
			@PathVariable(value = "korisnickoIme") @Valid String korisnickoIme) {
		Set<AktivnostInfo> aktivnosti = aktivnostService.vratiInformacijeOAktivnostimaKorisnika(korisnickoIme);
		if (aktivnosti.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(aktivnosti, HttpStatus.OK);
	}

	@PutMapping("/zavrsi/{id}")
	public ResponseEntity<Void> zavrsiAktivnost(@PathVariable(value = "id") @Valid Integer id) {
		aktivnostService.zavrsiAktivnost(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/masa")
	public ResponseEntity<Boolean> unesiMasu(@RequestBody @Valid MasaZahtjev zahtjev) {
		boolean dodato = aktivnostService.unesiMasu(zahtjev);
		return new ResponseEntity<>(dodato, HttpStatus.CREATED);
	}

	@GetMapping("/masa/{korisnickoIme}")
	public ResponseEntity<Set<MasaOdgovor>> vratiSveIzmjereneMaseKorisnika(
			@PathVariable(value = "korisnickoIme") @Valid String korisnickoIme) {
		Set<MasaOdgovor> mase = aktivnostService.vratiSveIzmjereneMaseKorisnika(korisnickoIme);
		if (mase.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(mase, HttpStatus.OK);
	}

}
