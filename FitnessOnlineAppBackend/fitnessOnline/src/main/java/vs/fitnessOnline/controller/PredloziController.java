package vs.fitnessOnline.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.PredlogVjezbeOdgovor;
import vs.fitnessOnline.service.PredloziService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/predlozi")
public class PredloziController {

	private final PredloziService predloziService;

	@GetMapping("")
	public ResponseEntity<Set<PredlogVjezbeOdgovor>> vratiPredloge() {
		Set<PredlogVjezbeOdgovor> vjezbe = predloziService.vratiPredloge();
		if (vjezbe.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(vjezbe, HttpStatus.OK);
	}

}
