package vs.fitnessOnline.controller;

import java.util.*;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.service.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/programi")
@RequiredArgsConstructor
public class FitnesProgramController {

	private final FitnesProgramService fitnesProgramService;
	private final SlikaService slikaService;

	@PostMapping("")
	public ResponseEntity<Integer> kreirajFitnesProgram(@RequestBody @Valid FitnesProgramZahtjev zahtjev) {
		Integer fitnesProgramId = fitnesProgramService.kreirajFitnesProgram(zahtjev);
		return new ResponseEntity<>(fitnesProgramId, HttpStatus.CREATED);
	}

	@DeleteMapping("/{fitnesProgramId}")
	public ResponseEntity<Void> odustaniOdKreiranjaFitnesPrograma(
			@PathVariable(value = "fitnesProgramId") @Valid Integer fitnesProgramId) {
		fitnesProgramService.odustaniOdKreiranjaFitnesPrograma(fitnesProgramId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/specifikacije")
	public ResponseEntity<Void> dodajFitnesProgramSpecifikaciju(
			@RequestBody @Valid FitnesProgramSpecifikacijaZahtjev zahtjev) {
		fitnesProgramService.dodajFitnesProgramSpecifikaciju(zahtjev);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/{korisnickoIme}/{fitnesProgramId}")
	public ResponseEntity<Void> zavrsiFitnesProgram(@PathVariable(value = "korisnickoIme") String korisnickoIme,
			@PathVariable(value = "fitnesProgramId") @Valid Integer fitnesProgramId) {
		fitnesProgramService.zavrsiFitnesProgram(korisnickoIme, fitnesProgramId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{korisnickoIme}/{fitnesProgramId}")
	public ResponseEntity<Void> obrisiFitnesProgram(@PathVariable(value = "korisnickoIme") String korisnickoIme,
			@PathVariable(value = "fitnesProgramId") @Valid Integer fitnesProgramId) {
		fitnesProgramService.obrisiFitnesProgram(korisnickoIme, fitnesProgramId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/javni/{fitnesPrograId}")
	public ResponseEntity<FitnesProgramDetaljiOdgovor> vratiFitnesProgramPoId(
			@PathVariable(value = "fitnesPrograId") Integer fitnesPrograId) {
		FitnesProgramDetaljiOdgovor program = fitnesProgramService.vratiFitnesProgramPoId(fitnesPrograId);
		return new ResponseEntity<>(program, HttpStatus.OK);
	}

	@GetMapping("/javni/kartice")
	public ResponseEntity<Map<String, Object>> vratiSveKarticeFitnesPrograma(
			@RequestParam(required = false) Integer kategorijaId,
			@RequestParam(defaultValue = "0") int trenutnaStranica, @RequestParam(defaultValue = "9") int brojElemenata,
			@RequestParam(defaultValue = "id,o") String[] sortiranje) {
		Map<String, Object> response = fitnesProgramService.vratiSveKarticeFitnesPrograma(kategorijaId,
				trenutnaStranica, brojElemenata, sortiranje);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/javni/kartice/pretraga")
	public ResponseEntity<Map<String, Object>> pretragaKarticaFitnesPrograma(
			@RequestBody @Valid PretragaZahtjev zahtjev, @RequestParam(defaultValue = "0") int trenutnaStranica,
			@RequestParam(defaultValue = "9") int brojElemenata,
			@RequestParam(defaultValue = "id,o") String[] sortiranje) {
		Map<String, Object> response = fitnesProgramService.pretragaKarticaFitnesPrograma(zahtjev, trenutnaStranica,
				brojElemenata, sortiranje);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/kartice/aktivno/korisnik/{korisnickoIme}")
	public ResponseEntity<Map<String, Object>> vratiKarticeAktivnihFitnesProgramaKorisnika(
			@PathVariable(value = "korisnickoIme") String korisnickoIme,
			@RequestParam(defaultValue = "0") int trenutnaStranica,
			@RequestParam(defaultValue = "4") int brojElemenata) {
		Map<String, Object> response = fitnesProgramService.vratiKarticeAktivnihFitnesProgramaKorisnika(korisnickoIme,
				trenutnaStranica, brojElemenata);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/kartice/zavrseno/korisnik/{korisnickoIme}")
	public ResponseEntity<Map<String, Object>> vratiKarticeZavrsenihFitnesProgramaKorisnika(
			@PathVariable(value = "korisnickoIme") String korisnickoIme,
			@RequestParam(defaultValue = "0") int trenutnaStranica,
			@RequestParam(defaultValue = "4") int brojElemenata) {
		Map<String, Object> response = fitnesProgramService.vratiKarticeZavrsenihFitnesProgramaKorisnika(korisnickoIme,
				trenutnaStranica, brojElemenata);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/kartice/kupljeno/korisnik/{korisnickoIme}")
	public ResponseEntity<Map<String, Object>> vratiKarticeKupljenihFitnesProgramaKorisnika(
			@PathVariable(value = "korisnickoIme") String korisnickoIme,
			@RequestParam(defaultValue = "0") int trenutnaStranica, @RequestParam(defaultValue = "9") int brojElemenata,
			@RequestParam(defaultValue = "id,o") String[] sortiranje) {
		Map<String, Object> response = fitnesProgramService.vratiKarticeKupljenihFitnesProgramaKorisnika(korisnickoIme,
				trenutnaStranica, brojElemenata, sortiranje);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/statistika/{korisnickoIme}")
	public ResponseEntity<FitnesProgramStatistikaOdgovor> vratiStatistikuFitnesPrograma(
			@PathVariable(value = "korisnickoIme") String korisnickoIme) {
		FitnesProgramStatistikaOdgovor statistika = fitnesProgramService.vratiStatistikuFitnesPrograma(korisnickoIme);
		return new ResponseEntity<>(statistika, HttpStatus.OK);
	}

	@PostMapping("/slika")
	public ResponseEntity<Void> dodajSliku(@RequestParam("file") @Valid MultipartFile file) {
		try {
			slikaService.sacuvaj(file);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	// Kao da ne treba.
//	@GetMapping("/javni/slika/{proizvodId}/poster")
//	public ResponseEntity<SlikaInfo> vratiPoster(@PathVariable Integer fitnesProgramId) {
//		SlikaInfo fileInfos = slikaService.vratiPoster(fitnesProgramId).map(path -> {
//			String filename = path.getFileName().toString();
//			String url = MvcUriComponentsBuilder
//					.fromMethodName(FitnesProgramController.class, "vratiSliku", path.getFileName().toString()).build()
//					.toString();
//			return new SlikaInfo(filename, url);
//		}).findFirst().get();
//		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//	}

	@GetMapping("/javni/slika/{filename:.+}")
	public ResponseEntity<Resource> vratiSliku(@PathVariable String filename) {
		Resource file = slikaService.vratiSliku(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/komentar/{fitnesProgramId}")
	public ResponseEntity<Map<String, Object>> vratiKomentareFitnesPrograma(
			@PathVariable(value = "fitnesProgramId") Integer fitnesProgramId,
			@RequestParam(defaultValue = "0") int trenutnaStranica,
			@RequestParam(defaultValue = "4") int brojElemenata) {
		Map<String, Object> response = fitnesProgramService.vratiKomentareFitnesPrograma(fitnesProgramId,
				trenutnaStranica, brojElemenata);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/komentar")
	public ResponseEntity<Void> dodajKomentar(@RequestBody @Valid FitnesProgramKomentarZahtjev zahtjev) {
		fitnesProgramService.dodajKomentar(zahtjev);
		return ResponseEntity.ok().build();
	}

}
