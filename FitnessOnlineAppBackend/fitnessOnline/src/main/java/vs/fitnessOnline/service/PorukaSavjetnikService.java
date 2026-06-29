package vs.fitnessOnline.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;

@Service
@RequiredArgsConstructor
public class PorukaSavjetnikService {

	private final SavjetnikRepository savjetnikRepository;
	private final KorisnikRepository korisnikRepository;
	private final PorukaSavjetnikRepository porukaSavjetnikRepository;
	private final ModelMapper modelMapper;
	private final MailService mailService;

	public Set<String> vratiImenaSavjetnika() {
		List<Savjetnik> savjetnici = savjetnikRepository.findAll();
		return savjetnici.stream().map(savjetnik -> savjetnik.getKorisnickoIme()).collect(Collectors.toSet());
	}

	public void posaljiPoruku(PorukaSavjetnikZahtjev zahtjev) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		Savjetnik savjetnik = savjetnikRepository.findByKorisnickoIme(zahtjev.getImeSavjetnika())
				.orElseThrow(() -> new EntitetNijePronadjenException("Savjetnik nije pronađen"));
		PorukaSavjetnik poruka = new PorukaSavjetnik();
		poruka.setNaslovPoruke(zahtjev.getNaslovPoruke());
		poruka.setTekstPoruke(zahtjev.getTekstPoruke());
		poruka.setVrijemePoruke(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
		poruka.setProcitana(false);
		poruka.setKorisnik(korisnik);
		poruka.setSavjetnik(savjetnik);
		porukaSavjetnikRepository.saveAndFlush(poruka);
	}

	public Set<PorukaSavjetnikOdgovor> prikaziSvePorukeSavjetnika(String imeSavjetnika) {
		Savjetnik savjetnik = savjetnikRepository.findByKorisnickoIme(imeSavjetnika)
				.orElseThrow(() -> new EntitetNijePronadjenException("Savjetnik nije pronađen"));
		List<PorukaSavjetnik> poruke = porukaSavjetnikRepository.findBySavjetnik(savjetnik);
		return poruke.stream().map(this::mapirajUDTO).collect(Collectors.toSet());
	}

	public PorukaSavjetnikOdgovor prikaziPoruku(Integer id) {
		Optional<PorukaSavjetnik> porukaOptional = porukaSavjetnikRepository.findById(id);
		if (porukaOptional.isPresent()) {
			return modelMapper.map(porukaOptional.get(), PorukaSavjetnikOdgovor.class);
		}
		return null;
	}

	public PorukaSavjetnikOdgovor oznaciKaoProcitanu(Integer id) {
		Optional<PorukaSavjetnik> porukaOptional = porukaSavjetnikRepository.findById(id);
		if (porukaOptional.isPresent()) {
			PorukaSavjetnik poruka = porukaOptional.get();
			PorukaSavjetnikOdgovor porukaDTO = modelMapper.map(porukaOptional.get(), PorukaSavjetnikOdgovor.class);
			poruka.setProcitana(true);
			porukaSavjetnikRepository.save(poruka);
			return porukaDTO;
		}
		return null;
	}

	public boolean posaljiOdgovor(Integer id, OdgovorSavjetnika odgovor) {
		boolean poslato = false;
		try {
			Optional<PorukaSavjetnik> optionalPoruka = porukaSavjetnikRepository.findById(id);
			if (optionalPoruka.isPresent()) {
				PorukaSavjetnik poruka = optionalPoruka.get();
				if (mailService.posaljiOdgovorSavjetnika(odgovor)) {
					poruka.setNaslovOdgovor(odgovor.getNaslov());
					poruka.setTekstOdgovor(odgovor.getTekst());
					poruka.setVrijemeOdgovor(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
					poruka.setPrilog(odgovor.getPrilog().getOriginalFilename());
					porukaSavjetnikRepository.save(poruka);
					poslato = true;
				}
			}
		} catch (Exception ex) {
			poslato = false;
		}
		return poslato;
	}

	private PorukaSavjetnikOdgovor mapirajUDTO(PorukaSavjetnik poruka) {
		PorukaSavjetnikOdgovor dto = modelMapper.map(poruka, PorukaSavjetnikOdgovor.class);
		dto.setKorisnickoIme(poruka.getKorisnik().getKorisnickoIme());
		dto.setEmail(poruka.getKorisnik().getEmail());
		return dto;
	}

}
