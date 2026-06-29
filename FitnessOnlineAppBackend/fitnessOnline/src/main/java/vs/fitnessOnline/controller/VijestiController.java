package vs.fitnessOnline.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.VijestiOdgovor;
import vs.fitnessOnline.service.VijestiService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vijesti")
public class VijestiController {

	private final VijestiService vijestiService;

	@GetMapping("")
	public ResponseEntity<Set<VijestiOdgovor>> vratiNajnovijeVijesti() {
		Set<VijestiOdgovor> vijesti = vijestiService.vratiNajnovijeVijesti();
		if (vijesti.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(vijesti, HttpStatus.OK);
	}

}
