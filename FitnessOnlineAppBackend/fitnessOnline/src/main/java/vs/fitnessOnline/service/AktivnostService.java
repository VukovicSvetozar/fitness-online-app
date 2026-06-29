package vs.fitnessOnline.service;

import java.time.LocalDate;
import java.util.*;
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
public class AktivnostService {

	private final AktivnostRepository aktivnostRepository;
	private final KorisnikRepository korisnikRepository;
	private final KategorijaRepository kategorijaRepository;
	private final MasaKorisnikaRepository masaKorisnikaRepository;
	private final ModelMapper modelMapper;

	public void kreirajAktivnsot(AktivnostZahtjev zahtjev) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		Kategorija kategorija = kategorijaRepository.findById(zahtjev.getKategorijaId())
				.orElseThrow(() -> new EntitetNijePronadjenException(
						"Kategorija sa id-em " + zahtjev.getKategorijaId() + " nije pronađena."));
		Aktivnost aktivnost = modelMapper.map(zahtjev, Aktivnost.class);
		aktivnost.setDatum(LocalDate.now().toString());
		aktivnost.setKategorija(kategorija);
		aktivnost.setKorisnik(korisnik);
		aktivnost.setZavrseno(false);
		aktivnost.setId(null);
		aktivnostRepository.saveAndFlush(aktivnost);
	}

	public List<AktivnostOdgovor> vratiSveAktivnostiPoDatumu(String korisnickoIme, String datum) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		List<Aktivnost> aktivnosti = aktivnostRepository.findByKorisnikAndDatum(korisnik, datum);
		return aktivnosti.stream().map(this::mapirajAktivnostiUDTO).collect(Collectors.toList());
	}

	public String vratiDnevnikKorisnika(String korisnickoIme) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		StringBuilder sb = new StringBuilder();
		sb.append("Dnevnik aktivnosti\n\n");
		sb.append("Korisnik: " + korisnickoIme + "\n\n");

		Set<Aktivnost> aktivnosti = aktivnostRepository.findByKorisnik(korisnik);
		aktivnosti.forEach(aktivnost -> {
			sb.append("naziv: " + aktivnost.getNaziv() + "\n");
			sb.append("kategorija: " + aktivnost.getKategorija().getNaziv() + "\n");
			sb.append("intezitet: " + aktivnost.getIntezitet() + "\n");
			sb.append("trajanje: " + aktivnost.getTrajanje() + "\n");
			sb.append("broj ponavljanja: " + aktivnost.getBrojPonavljanja() + "\n");
			sb.append("datum: " + aktivnost.getDatum() + "\n");
			sb.append("zavrseno: " + (aktivnost.isZavrseno() ? "da" : "ne") + "\n");
			sb.append("\n");
		});
		return sb.toString();
	}

	public Set<AktivnostInfo> vratiInformacijeOAktivnostimaKorisnika(String korisnickoIme) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		Set<Aktivnost> aktivnosti = aktivnostRepository.findByKorisnik(korisnik);
		Set<AktivnostInfo> info = new HashSet<>();

		Map<String, List<Aktivnost>> grupisaniPoDatumu = aktivnosti.stream()
				.collect(Collectors.groupingBy(Aktivnost::getDatum));
		info = grupisaniPoDatumu.entrySet().stream().map(e -> {
			String datum = e.getKey();
			List<Aktivnost> aktivnostiOdredjenogDatuma = e.getValue();
			long ukupno = aktivnostiOdredjenogDatuma.size();
			long brojZavrsenih = 0;
			for (Aktivnost aktivnost : aktivnostiOdredjenogDatuma) {
				if (aktivnost.isZavrseno())
					brojZavrsenih++;
			}
			AktivnostInfo aktivnostInfo = new AktivnostInfo();
			aktivnostInfo.setDatum(datum);
			aktivnostInfo.setUkupno((int) ukupno);
			aktivnostInfo.setBrojZavrsenih((int) brojZavrsenih);
			aktivnostInfo.setKorisnickoIme(korisnickoIme);
			return aktivnostInfo;
		}).collect(Collectors.toSet());
		return info;
	}

	public void zavrsiAktivnost(Integer id) {
		Aktivnost aktivnost = aktivnostRepository.findById(id)
				.orElseThrow(() -> new EntitetNijePronadjenException("Aktivnost nije pronađena"));
		aktivnost.setZavrseno(true);
		aktivnostRepository.saveAndFlush(aktivnost);
	}

	public boolean unesiMasu(MasaZahtjev zahtjev) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		if (masaKorisnikaRepository.existsByDatumAndKorisnik(LocalDate.now().toString(), korisnik))
			return false;
		MasaKorisnika masa = modelMapper.map(zahtjev, MasaKorisnika.class);
		masa.setDatum(LocalDate.now().toString());
		masa.setKorisnik(korisnik);
		masa.setId(null);
		masaKorisnikaRepository.saveAndFlush(masa);
		return true;
	}

	public Set<MasaOdgovor> vratiSveIzmjereneMaseKorisnika(String korisnickoIme) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		List<MasaKorisnika> mase = masaKorisnikaRepository.findByKorisnik(korisnik);
		return mase.stream().map(this::mapirajMaseKorisnikaUDTO).collect(Collectors.toSet());
	}

	private AktivnostOdgovor mapirajAktivnostiUDTO(Aktivnost aktivnost) {
		AktivnostOdgovor dto = modelMapper.map(aktivnost, AktivnostOdgovor.class);
		dto.setNazivKategorije(aktivnost.getKategorija().getNaziv());
		return dto;
	}

	private MasaOdgovor mapirajMaseKorisnikaUDTO(MasaKorisnika masa) {
		MasaOdgovor dto = modelMapper.map(masa, MasaOdgovor.class);
		return dto;
	}

}