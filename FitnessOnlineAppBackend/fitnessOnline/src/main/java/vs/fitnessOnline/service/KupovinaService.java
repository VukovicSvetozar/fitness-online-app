package vs.fitnessOnline.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;

@Service
@RequiredArgsConstructor
public class KupovinaService {

	private final PlacanjeRepository placanjeRepository;
	private final UcestvovanjeRepository ucestvovanjeRepository;
	private final FitnesProgramRepository fitnesProgramRepository;
	private final KorisnikRepository korisnikRepository;

	public String kupi(KupovinaZahtjev zahtjev) {
		FitnesProgram program = fitnesProgramRepository.findById(zahtjev.getFitnesProgramId())
				.orElseThrow(() -> new EntitetNijePronadjenException(
						"Fitnes program sa id: " + zahtjev.getFitnesProgramId() + " nije pronađen"));
		Korisnik kupac = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoImeKupca())
				.orElseThrow(() -> new EntitetNijePronadjenException("Kupac nije pronađen"));
		Korisnik prodavac = korisnikRepository.findById(program.getInstruktor().getId())
				.orElseThrow(() -> new EntitetNijePronadjenException("Prodavac nije pronađen"));
		if (program.getInstruktor().getId() == kupac.getId())
			return "Ne možete kupiti sopstveni fitnes program!";
		if (ucestvovanjeRepository.existsByFitnesProgramAndKupac(program, kupac))
			return "Dati fitnes program ste već kupili";
		Placanje placanje = Placanje.builder().brojKartice(zahtjev.getBrojKartice())
				.payPalRacun(zahtjev.getPayPalRacun()).licno(zahtjev.isLicno()).build();
		placanjeRepository.save(placanje);
		Ucestvovanje ucestvovanje = Ucestvovanje.builder().vrijeme(LocalDate.now().toString()).placanje(placanje)
				.fitnesProgram(program).prodavac(prodavac).kupac(kupac).build();
		ucestvovanjeRepository.save(ucestvovanje);
		return "Kupovina je uspješno realizovana!";
	}

}
