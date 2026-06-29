package vs.fitnessOnline.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.service.PretplataService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pretplata")
public class PretplataController {

	private final PretplataService pretplataService;

	@Transactional
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{korisnickoIme}/{kategorijaId}")
	public ResponseEntity<Boolean> pretplatiKorisnika(@PathVariable @Valid String korisnickoIme,
			@PathVariable @Valid Integer kategorijaId) {
		boolean pretplacen = pretplataService.pretplatiKorisnika(korisnickoIme, kategorijaId);
		return ResponseEntity.ok(pretplacen);
	}
	
}
