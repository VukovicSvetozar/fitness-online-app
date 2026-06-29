package vs.fitnessOnline.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.service.*;

@RestController
@RequestMapping("/api/porukeSavjetnika")
public class PorukaSavjetnikController {

	@Autowired
	private PorukaSavjetnikService porukaService;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/imena")
	public ResponseEntity<Set<String>> vratiImenaSavjetnika() {
		Set<String> korisnickaImena = porukaService.vratiImenaSavjetnika();
		if (korisnickaImena.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(korisnickaImena, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("")
	public ResponseEntity<Void> posaljiPoruku(@RequestBody PorukaSavjetnikZahtjev zahtjev) {
		porukaService.posaljiPoruku(zahtjev);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/poruke/{imeSavjetnika}")
	public ResponseEntity<Set<PorukaSavjetnikOdgovor>> prikaziSvePorukeSavjetnika(
			@PathVariable @Valid String imeSavjetnika) {
		Set<PorukaSavjetnikOdgovor> poruke = porukaService.prikaziSvePorukeSavjetnika(imeSavjetnika);
		if (poruke.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(poruke, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/{id}")
	public ResponseEntity<PorukaSavjetnikOdgovor> prikaziPoruku(@PathVariable(value = "id") Integer id) {
		PorukaSavjetnikOdgovor poruka = porukaService.prikaziPoruku(id);
		if (poruka != null) {
			return ResponseEntity.ok().body(poruka);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PutMapping("/{id}/procitana")
	public ResponseEntity<Void> oznaciKaoProcitanu(@PathVariable(value = "id") Integer id) {
		PorukaSavjetnikOdgovor promjenjenaPoruka = porukaService.oznaciKaoProcitanu(id);
		if (promjenjenaPoruka == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PutMapping("/{id}/odgovor")
	public ResponseEntity<Boolean> posaljiOdgovor(@PathVariable(value = "id") Integer id,
			@ModelAttribute OdgovorSavjetnika odgovor) {
		boolean poslato = porukaService.posaljiOdgovor(id, odgovor);
		return ResponseEntity.ok(poslato);
	}

}
