package vs.fitnessOnline.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.*;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;

@Service
@RequiredArgsConstructor
public class PorukaKorisniciService {

	private final KorisnikRepository korisnikRepository;
	private final PorukaKorisnikRepository porukaKorisnikRepository;
	private final ModelMapper modelMapper;

	public Set<String> vratiKorisnickaImena(String korisnickoIme) {
		List<Korisnik> korisnici = korisnikRepository.findAll();
		return korisnici.stream().map(korisnik -> korisnik.getKorisnickoIme()).filter(ime -> !ime.equals(korisnickoIme))
				.collect(Collectors.toSet());
	}

	public void dodajPoruku(PorukaKorisnikZahtjev zahtjev) {
		Korisnik posiljalac = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoImePosiljalac())
				.orElseThrow(() -> new EntitetNijePronadjenException("Posiljalac nije pronađen"));
		Korisnik primalac = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoImePrimalac())
				.orElseThrow(() -> new EntitetNijePronadjenException("Primalac nije pronađen"));
		PorukaKorisnik poruka = new PorukaKorisnik();
		poruka.setTekstPoruke(zahtjev.getTekstPoruke());
		poruka.setVrijemePoruke(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
		poruka.setPosiljalac(posiljalac);
		poruka.setPrimalac(primalac);
		porukaKorisnikRepository.saveAndFlush(poruka);
	}

	public Set<PorukaKorisnikOdgovor> vratiPoruke(String korisnickoImePrviUcesnik, String korisnickoImeDrugiUcesnik) {
		List<PorukaKorisnik> porukePrvogKaDrugom = porukaKorisnikRepository
				.findByPosiljalacKorisnickoImeAndPrimalacKorisnickoIme(korisnickoImePrviUcesnik, korisnickoImeDrugiUcesnik);
		List<PorukaKorisnik> porukeDrugogKaPrvom = porukaKorisnikRepository
				.findByPosiljalacKorisnickoImeAndPrimalacKorisnickoIme(korisnickoImeDrugiUcesnik, korisnickoImePrviUcesnik);
		List<PorukaKorisnik> svePoruke = new ArrayList<>();
		svePoruke.addAll(porukePrvogKaDrugom);
		svePoruke.addAll(porukeDrugogKaPrvom);
		svePoruke.sort(Comparator.comparing(PorukaKorisnik::getVrijemePoruke));
		return svePoruke.stream().map(this::mapirajPorukaKorisnikUDTO).collect(Collectors.toSet());
	}

	private PorukaKorisnikOdgovor mapirajPorukaKorisnikUDTO(PorukaKorisnik poruka) {
		PorukaKorisnikOdgovor dto = modelMapper.map(poruka, PorukaKorisnikOdgovor.class);
		dto.setKorisnickoImePosiljalac(poruka.getPosiljalac().getKorisnickoIme());
		return dto;
	}

}
