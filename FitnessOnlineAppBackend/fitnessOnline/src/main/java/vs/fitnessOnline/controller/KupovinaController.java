package vs.fitnessOnline.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.KupovinaZahtjev;
import vs.fitnessOnline.service.KupovinaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kupovina")
public class KupovinaController {

	private final KupovinaService kupovinaService;

	@PostMapping("")
	public ResponseEntity<String> kupi(@RequestBody @Valid KupovinaZahtjev zahtjev) {
		String odgovor = kupovinaService.kupi(zahtjev);
		return ResponseEntity.ok(odgovor);
	}

}
