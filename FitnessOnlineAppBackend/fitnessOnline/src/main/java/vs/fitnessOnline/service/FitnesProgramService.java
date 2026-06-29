package vs.fitnessOnline.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.*;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.controller.*;
import vs.fitnessOnline.dto.*;
import vs.fitnessOnline.entity.*;
import vs.fitnessOnline.exception.*;
import vs.fitnessOnline.repository.*;

@Service
@RequiredArgsConstructor
public class FitnesProgramService {

	private final FitnesProgramRepository fitnesProgramRepository;
	private final KorisnikRepository korisnikRepository;
	private final KategorijaRepository kategorijaRepository;
	private final SpecifikacijaRepository specifikacijaRepository;
	private final FitnesProgramSpecifikacijaRepository fitnesProgramSpecifikacijaRepository;
	private final FitnesProgramKomentarRepository fitnesProgramKomentarRepository;
	private final SlikaService slikaService;
	private final ModelMapper modelMapper;

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("r")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("o")) {
			return Sort.Direction.DESC;
		}
		return Sort.Direction.ASC;
	}

	private List<FitnesProgramKarticaOdgovor> vratiSveKarticeFitnesPrograma(List<FitnesProgram> kartice) {
		return kartice.stream().map(this::mapirajKarticeFitnesProgramaUDTO).collect(Collectors.toList());
	}

	private FitnesProgramKarticaOdgovor mapirajKarticeFitnesProgramaUDTO(FitnesProgram program) {
		FitnesProgramKarticaOdgovor dto = modelMapper.map(program, FitnesProgramKarticaOdgovor.class);
		Kategorija kategorija = kategorijaRepository.findById(program.getKategorija().getId())
				.orElseThrow(() -> new EntitetNijePronadjenException(
						"Kategorija sa id-em " + program.getKategorija().getId() + " nije pronađena."));
		dto.setNazivKategorije(kategorija.getNaziv());
		SlikaInfo slikaInfo = slikaService.vratiPoster(program.getId()).map(path -> {
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(FitnesProgramController.class, "vratiSliku", path.getFileName().toString()).build()
					.toString();
			return new SlikaInfo(filename, url);
		}).findFirst().orElseThrow(() -> new RuntimeException("Poster nije pronađen."));
		dto.setPosterUrl(slikaInfo.getUrl());
		return dto;
	}

	private List<String> ucitajSlikeFitnesPrograma(Integer fitnesProgramId) {
		List<SlikaInfo> fileInfos = slikaService.vratiSlikeFitnesPrograma(fitnesProgramId).map(path -> {
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(FitnesProgramController.class, "vratiSliku", path.getFileName().toString()).build()
					.toString();
			return new SlikaInfo(filename, url);
		}).collect(Collectors.toList());
		List<String> slikeFitnesProgramaUrl = fileInfos.stream().map(fi -> fi.getUrl()).collect(Collectors.toList());
		return slikeFitnesProgramaUrl;
	}

	private FitnesProgramKomentarOdgovor mapirajKomentareUDTO(FitnesProgramKomentar komentar) {
		FitnesProgramKomentarOdgovor dto = modelMapper.map(komentar, FitnesProgramKomentarOdgovor.class);
		Korisnik korisnik = korisnikRepository.findById(komentar.getKorisnik().getId())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		dto.setAvatarUrl(korisnik.getAvatarUrl());
		dto.setKorisnickoIme(korisnik.getKorisnickoIme());
		return dto;
	}

	public Integer kreirajFitnesProgram(FitnesProgramZahtjev zahtjev) {
		if (!kategorijaRepository.existsById(zahtjev.getKategorijaId())) {
			throw new EntitetNijePronadjenException(
					"Kategorija sa id-em " + zahtjev.getKategorijaId() + " nije pronađena.");
		}
		Korisnik instruktor = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme())
				.orElseThrow(() -> new EntitetNijePronadjenException("Instruktor nije pronađen"));
		FitnesProgram program = modelMapper.map(zahtjev, FitnesProgram.class);
		program.setStatus(FitnesProgramStatus.AKTIVAN.name());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatiranDatum = LocalDateTime.now().format(format);
		program.setVrijemeObjave(formatiranDatum);
		program.setInstruktor(instruktor);
		program.setId(null);
		FitnesProgram dodatiProgram = fitnesProgramRepository.saveAndFlush(program);
		return dodatiProgram.getId();
	}

	public void odustaniOdKreiranjaFitnesPrograma(Integer id) {
		FitnesProgram program = fitnesProgramRepository.findById(id)
				.orElseThrow(() -> new EntitetNijePronadjenException("Fitnes program nije pronađen."));
		fitnesProgramRepository.delete(program);
	}

	public void dodajFitnesProgramSpecifikaciju(FitnesProgramSpecifikacijaZahtjev zahtjev) {
		if (!fitnesProgramRepository.existsById(zahtjev.getFitnesProgramId())) {
			throw new EntitetNijePronadjenException(
					"Fitnes program sa id-em " + zahtjev.getFitnesProgramId() + " nije pronađen.");
		}
		if (!specifikacijaRepository.existsById(zahtjev.getSpecifikacijaId())) {
			throw new EntitetNijePronadjenException(
					"Specifikacija sa id-em " + zahtjev.getSpecifikacijaId() + " nije pronađena.");
		}
		FitnesProgramSpecifikacija fitnesProgramSpecifikacija = modelMapper.map(zahtjev,
				FitnesProgramSpecifikacija.class);
		fitnesProgramSpecifikacijaRepository.save(fitnesProgramSpecifikacija);
	}

	public void zavrsiFitnesProgram(String korisnickoIme, Integer id) {
		FitnesProgram program = fitnesProgramRepository.findById(id)
				.orElseThrow(() -> new EntitetNijePronadjenException("Fitnes program nije pronađen."));
		Korisnik instruktor = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Instruktor nije pronađen."));
		if (FitnesProgramStatus.ZAVRSEN.toString().equals(program.getStatus()))
			throw new PogresanZahtjevException("Fitnes program nije aktivan.");
		if (program.getInstruktor().getId() != instruktor.getId())
			throw new PogresanZahtjevException("Dozvoljeno je završiti jedino sopstveni fitnes program.");
		program.setStatus(FitnesProgramStatus.ZAVRSEN.toString());
		fitnesProgramRepository.saveAndFlush(program);
	}

	public void obrisiFitnesProgram(String korisnickoIme, Integer id) {
		FitnesProgram program = fitnesProgramRepository.findById(id)
				.orElseThrow(() -> new EntitetNijePronadjenException("Fitnes program nije pronađen."));
		Korisnik instruktor = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Instruktor nije pronađen."));
		if (FitnesProgramStatus.AKTIVAN.toString().equals(program.getStatus()))
			throw new PogresanZahtjevException("Dozvoljeno je obrisati jedino završeni fitnes program.");
		if (program.getInstruktor().getId() != instruktor.getId())
			throw new PogresanZahtjevException("Dozvoljeno je obrisati jedino sopstveni fitnes program.");
		fitnesProgramRepository.delete(program);
		slikaService.obrisiSlikeFitnesPrograma(id);
	}

	public FitnesProgramDetaljiOdgovor vratiFitnesProgramPoId(Integer fitnesPrograId) {
		FitnesProgram program = fitnesProgramRepository.findById(fitnesPrograId)
				.orElseThrow(() -> new EntitetNijePronadjenException(
						"Fitnes program sa id-em " + fitnesPrograId + " nije pronađen."));
		FitnesProgramDetaljiOdgovor programDetalji = modelMapper.map(program, FitnesProgramDetaljiOdgovor.class);
		Korisnik korisnik = korisnikRepository.findById(program.getInstruktor().getId())
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		programDetalji.setAvatarUrl(korisnik.getAvatarUrl());
		programDetalji.setDatumPristupa(korisnik.getDatumPristupa());
		programDetalji.setKorisnickoIme(korisnik.getKorisnickoIme());
		programDetalji.setGrad(korisnik.getGrad());
		programDetalji.setEmail(korisnik.getEmail());
		programDetalji.setBrojTelefona(korisnik.getBrojTelefona());
		programDetalji.setNazivKategorije(program.getKategorija().getNaziv());

		int kategorijaId = program.getKategorija().getId();
		Set<Specifikacija> specifikacije = specifikacijaRepository.findByKategorijaId(kategorijaId);
		List<FitnesProgramSpecifikacija> listaFitnesProgramSpecifikacija = fitnesProgramSpecifikacijaRepository
				.findAll();
		Map<String, String> specificneOsobine = new HashMap<>();
		specifikacije.forEach(s -> {
			Optional<FitnesProgramSpecifikacija> odgovarajucaFitnesProgramSpecifikacija = listaFitnesProgramSpecifikacija
					.stream().filter(ps -> ps.getId().getFitnesProgram().getId() == program.getId()
							&& ps.getId().getSpecifikacija().getId() == s.getId())
					.findFirst();
			odgovarajucaFitnesProgramSpecifikacija.ifPresent(ps -> {
				specificneOsobine.put(s.getNaziv(), ps.getVrijednost());
			});
		});
		programDetalji.setSpecificneOsobine(specificneOsobine);
		programDetalji.setSlikeFitnesProgramaUrl(ucitajSlikeFitnesPrograma(fitnesPrograId));

		return programDetalji;
	}

	public Map<String, Object> vratiSveKarticeFitnesPrograma(@RequestParam(required = false) Integer kategorijaId,
			@RequestParam(defaultValue = "0") int trenutnaStranica, @RequestParam(defaultValue = "9") int brojElemenata,
			@RequestParam(defaultValue = "id,o") String[] sortiranje) {

		List<Order> orders = new ArrayList<Order>();
		Map<String, Object> odgovor = new HashMap<>();

		if (sortiranje[0].contains(",")) {
			for (String sortOrder : sortiranje) {
				String[] _sort = sortOrder.split(",");
				orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
			}
		} else {
			orders.add(new Order(getSortDirection(sortiranje[1]), sortiranje[0]));
		}

		List<FitnesProgram> listaFitnesPrograma = new ArrayList<FitnesProgram>();
		List<FitnesProgramKarticaOdgovor> listaKarticaFitnesPrograma = new ArrayList<FitnesProgramKarticaOdgovor>();

		Pageable pagingSort = PageRequest.of(trenutnaStranica, brojElemenata, Sort.by(orders));
		Page<FitnesProgram> pageKarticaFitnesProgrami;

		if (kategorijaId == null) {
			pageKarticaFitnesProgrami = fitnesProgramRepository.findAll(pagingSort);

		} else {
			Kategorija kategorija = kategorijaRepository.findById(kategorijaId)
					.orElseThrow(() -> new EntitetNijePronadjenException("Kategorija nije pronađena."));
			pageKarticaFitnesProgrami = fitnesProgramRepository.findByKategorija(kategorija, pagingSort);
		}

		listaFitnesPrograma = pageKarticaFitnesProgrami.getContent();
		listaFitnesPrograma = listaFitnesPrograma.stream()
				.filter(program -> FitnesProgramStatus.AKTIVAN.name().equals(program.getStatus()))
				.collect(Collectors.toList());
		listaKarticaFitnesPrograma = vratiSveKarticeFitnesPrograma(listaFitnesPrograma);
		odgovor.put("kartice", listaKarticaFitnesPrograma);
		odgovor.put("trenutnaStranica", pageKarticaFitnesProgrami.getNumber());
		odgovor.put("ukupnoElemenata", pageKarticaFitnesProgrami.getTotalElements());
		odgovor.put("brojStranica", pageKarticaFitnesProgrami.getTotalPages());
		return odgovor;
	}

	public Map<String, Object> pretragaKarticaFitnesPrograma(PretragaZahtjev zahtjev,
			@RequestParam(defaultValue = "0") int trenutnaStranica, @RequestParam(defaultValue = "9") int brojElemenata,
			@RequestParam(defaultValue = "id,o") String[] sortiranje) {

		List<Order> orders = new ArrayList<Order>();
		Map<String, Object> odgovor = new HashMap<>();
		try {
			if (sortiranje[0].contains(",")) {
				for (String sortOrder : sortiranje) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				orders.add(new Order(getSortDirection(sortiranje[1]), sortiranje[0]));
			}

			List<FitnesProgram> listaFitnesPrograma = new ArrayList<FitnesProgram>();

			if (zahtjev.getKategorijaId() == null) {
				listaFitnesPrograma = fitnesProgramRepository.findAll();
			} else {
				Kategorija kategorija = kategorijaRepository.findById(zahtjev.getKategorijaId())
						.orElseThrow(() -> new EntitetNijePronadjenException("Kategorija nije pronađena."));
				listaFitnesPrograma = fitnesProgramRepository.findByKategorija(kategorija);
			}
			if (zahtjev.getKorisnickoIme() != null) {
				listaFitnesPrograma = listaFitnesPrograma.stream()
						.filter(program -> program.getInstruktor().getKorisnickoIme() != null
								&& program.getInstruktor().getKorisnickoIme().equals(zahtjev.getKorisnickoIme()))
						.collect(Collectors.toList());
			}

			if (zahtjev.getNaziv() != null) {
				listaFitnesPrograma = listaFitnesPrograma.stream()
						.filter(program -> program.getNaziv() != null
								&& program.getNaziv().toLowerCase().contains(zahtjev.getNaziv().toLowerCase()))
						.collect(Collectors.toList());
			}

			if (zahtjev.getTipLokacije() != null) {
				listaFitnesPrograma = listaFitnesPrograma.stream()
						.filter(program -> program.getTipLokacije() != null
								&& program.getTipLokacije().equals(zahtjev.getTipLokacije()))
						.collect(Collectors.toList());
			}

			if (zahtjev.getMinCijena() != null && zahtjev.getMaxCijena() != null) {
				listaFitnesPrograma = listaFitnesPrograma.stream()
						.filter(program -> program.getCijena() >= zahtjev.getMinCijena()
								&& program.getCijena() <= zahtjev.getMaxCijena())
						.collect(Collectors.toList());
			}

			if (zahtjev.getDatumOd() != null && zahtjev.getDatumDo() != null) {
				LocalDate dateOd = LocalDate.parse(zahtjev.getDatumOd());
				LocalDate dateDo = LocalDate.parse(zahtjev.getDatumDo());
				listaFitnesPrograma = listaFitnesPrograma.stream().filter(program -> {
					LocalDate programDatum = LocalDate.parse(program.getVrijemeObjave());
					return programDatum.isAfter(dateOd.minusDays(1)) && programDatum.isBefore(dateDo.plusDays(1));
				}).collect(Collectors.toList());
			}

			if (zahtjev.getNivoTezine() != null) {
				listaFitnesPrograma = listaFitnesPrograma.stream()
						.filter(program -> program.getNivoTezine() != null
								&& program.getNivoTezine().equals(zahtjev.getNivoTezine()))
						.collect(Collectors.toList());
			}

			if (zahtjev.getMinTrajanje() != null && zahtjev.getMaxTrajanje() != null) {
				listaFitnesPrograma = listaFitnesPrograma.stream()
						.filter(program -> program.getTrajanje() >= zahtjev.getMinTrajanje()
								&& program.getTrajanje() <= zahtjev.getMaxTrajanje())
						.collect(Collectors.toList());
			}

			int indeksPocetka = trenutnaStranica * brojElemenata;
			int indeksKraja = Math.min((trenutnaStranica + 1) * brojElemenata, listaFitnesPrograma.size());
			int ukupnoElemenata = listaFitnesPrograma.size();
			int ukupanBrojStranica = ukupnoElemenata / brojElemenata + ((ukupnoElemenata % brojElemenata != 0) ? 1 : 0);
			if (indeksPocetka < indeksKraja)
				listaFitnesPrograma = listaFitnesPrograma.subList(indeksPocetka, indeksKraja);

			listaFitnesPrograma = listaFitnesPrograma.stream()
					.filter(program -> FitnesProgramStatus.AKTIVAN.name().equals(program.getStatus()))
					.collect(Collectors.toList());
			List<FitnesProgramKarticaOdgovor> listaKarticaFitnesPrograma = vratiSveKarticeFitnesPrograma(
					listaFitnesPrograma);

			odgovor.put("pronadjeneKartice", listaKarticaFitnesPrograma);
			odgovor.put("trenutnaStranica", trenutnaStranica);
			odgovor.put("ukupnoElemenata", ukupnoElemenata);
			odgovor.put("brojStranica", ukupanBrojStranica);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return odgovor;
	}

	public Map<String, Object> vratiKarticeAktivnihFitnesProgramaKorisnika(String korisnickoIme,
			@RequestParam(defaultValue = "0") int trenutnaStranica,
			@RequestParam(defaultValue = "4") int brojElemenata) {

		Map<String, Object> odgovor = new HashMap<>();

		List<FitnesProgram> listaFitnesPrograma = new ArrayList<FitnesProgram>();

		Korisnik instruktor = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen."));
		listaFitnesPrograma = fitnesProgramRepository.findByInstruktor(instruktor);
		List<FitnesProgram> listaAktivnihFitnesPrograma = listaFitnesPrograma.stream()
				.filter(program -> FitnesProgramStatus.AKTIVAN.name().equals(program.getStatus()))
				.collect(Collectors.toList());

		int indeksPocetka = trenutnaStranica * brojElemenata;
		int indeksKraja = Math.min((trenutnaStranica + 1) * brojElemenata, listaAktivnihFitnesPrograma.size());
		int ukupnoElemenata = listaAktivnihFitnesPrograma.size();
		int ukupanBrojStranica = ukupnoElemenata / brojElemenata + ((ukupnoElemenata % brojElemenata != 0) ? 1 : 0);
		if (indeksPocetka < indeksKraja)
			listaAktivnihFitnesPrograma = listaAktivnihFitnesPrograma.subList(indeksPocetka, indeksKraja);

		List<FitnesProgramKarticaOdgovor> listaAktivnihKarticaFitnesPrograma = vratiSveKarticeFitnesPrograma(
				listaAktivnihFitnesPrograma);

		odgovor.put("aktivneKartice", listaAktivnihKarticaFitnesPrograma);
		odgovor.put("trenutnaStranica", trenutnaStranica);
		odgovor.put("ukupnoElemenata", ukupnoElemenata);
		odgovor.put("brojStranica", ukupanBrojStranica);
		return odgovor;
	}

	public Map<String, Object> vratiKarticeZavrsenihFitnesProgramaKorisnika(String korisnickoIme,
			@RequestParam(defaultValue = "0") int trenutnaStranica,
			@RequestParam(defaultValue = "9") int brojElemenata) {

		Map<String, Object> odgovor = new HashMap<>();
		List<FitnesProgram> listaFitnesPrograma = new ArrayList<FitnesProgram>();

		Korisnik instruktor = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen."));
		listaFitnesPrograma = fitnesProgramRepository.findByInstruktor(instruktor);
		List<FitnesProgram> listaZavrsenihFitnesPrograma = listaFitnesPrograma.stream()
				.filter(program -> FitnesProgramStatus.ZAVRSEN.name().equals(program.getStatus()))
				.collect(Collectors.toList());

		int indeksPocetka = trenutnaStranica * brojElemenata;
		int indeksKraja = Math.min((trenutnaStranica + 1) * brojElemenata, listaZavrsenihFitnesPrograma.size());
		int ukupnoElemenata = listaZavrsenihFitnesPrograma.size();
		int ukupanBrojStranica = ukupnoElemenata / brojElemenata + ((ukupnoElemenata % brojElemenata != 0) ? 1 : 0);
		if (indeksPocetka < indeksKraja)
			listaZavrsenihFitnesPrograma = listaZavrsenihFitnesPrograma.subList(indeksPocetka, indeksKraja);

		List<FitnesProgramKarticaOdgovor> listaZavrsenihKarticaProizvoda = vratiSveKarticeFitnesPrograma(
				listaZavrsenihFitnesPrograma);

		odgovor.put("zavrseneKartice", listaZavrsenihKarticaProizvoda);
		odgovor.put("trenutnaStranica", trenutnaStranica);
		odgovor.put("ukupnoElemenata", ukupnoElemenata);
		odgovor.put("brojStranica", ukupanBrojStranica);
		return odgovor;
	}

	public Map<String, Object> vratiKarticeKupljenihFitnesProgramaKorisnika(String korisnickoIme,
			@RequestParam(defaultValue = "0") int trenutnaStranica, @RequestParam(defaultValue = "4") int brojElemenata,
			@RequestParam(defaultValue = "id,o") String[] sortiranje) {

		List<Order> orders = new ArrayList<Order>();
		Map<String, Object> odgovor = new HashMap<>();

		if (sortiranje[0].contains(",")) {
			for (String sortOrder : sortiranje) {
				String[] _sort = sortOrder.split(",");
				orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
			}
		} else {
			orders.add(new Order(getSortDirection(sortiranje[1]), sortiranje[0]));
		}

		List<FitnesProgram> listaFitnesPrograma = new ArrayList<FitnesProgram>();

		Pageable pagingSort = PageRequest.of(trenutnaStranica, brojElemenata, Sort.by(orders));
		Page<FitnesProgram> pageKarticaFitnesProgrami;

		Korisnik kupac = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen."));
		pageKarticaFitnesProgrami = fitnesProgramRepository.findByUcestvovanje_KupacId(kupac.getId(), pagingSort);
		listaFitnesPrograma = pageKarticaFitnesProgrami.getContent();
		List<FitnesProgramKarticaOdgovor> listaKupljenihKarticaFitnesPrograma = vratiSveKarticeFitnesPrograma(
				listaFitnesPrograma);

		odgovor.put("kupljeneKartice", listaKupljenihKarticaFitnesPrograma);
		odgovor.put("trenutnaStranica", pageKarticaFitnesProgrami.getNumber());
		odgovor.put("ukupnoElemenata", pageKarticaFitnesProgrami.getTotalElements());
		odgovor.put("brojStranica", pageKarticaFitnesProgrami.getTotalPages());
		return odgovor;
	}

	public FitnesProgramStatistikaOdgovor vratiStatistikuFitnesPrograma(String korisnickoIme) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen."));
		List<FitnesProgram> listaFitnesPrograma = fitnesProgramRepository.findByInstruktor(korisnik);
		List<FitnesProgram> listaAktivnihFitnesPrograma = listaFitnesPrograma.stream()
				.filter(program -> FitnesProgramStatus.AKTIVAN.name().equals(program.getStatus()))
				.collect(Collectors.toList());
		List<FitnesProgram> listaZavrsenihFitnesPrograma = listaFitnesPrograma.stream()
				.filter(program -> FitnesProgramStatus.ZAVRSEN.name().equals(program.getStatus()))
				.collect(Collectors.toList());
		List<FitnesProgram> listaKupljenihFitnesPrograma = fitnesProgramRepository
				.findByUcestvovanje_KupacId(korisnik.getId());
		FitnesProgramStatistikaOdgovor statistika = new FitnesProgramStatistikaOdgovor(
				listaAktivnihFitnesPrograma.size(), listaZavrsenihFitnesPrograma.size(),
				listaKupljenihFitnesPrograma.size());
		return statistika;
	}

	public Map<String, Object> vratiKomentareFitnesPrograma(Integer fitnesProgramId,
			@RequestParam(defaultValue = "0") int trenutnaStranica,
			@RequestParam(defaultValue = "9") int brojElemenata) {

		Map<String, Object> odgovor = new HashMap<>();
		List<FitnesProgramKomentar> listaKomentara = new ArrayList<>();

		Pageable pagingSort = PageRequest.of(trenutnaStranica, brojElemenata);
		Page<FitnesProgramKomentar> pageKomentariFitnesPrograma;

		FitnesProgram program = fitnesProgramRepository.findById(fitnesProgramId)
				.orElseThrow(() -> new EntitetNijePronadjenException("Fitnes program nije pronađen."));

		pageKomentariFitnesPrograma = fitnesProgramKomentarRepository.findByFitnesProgram(program, pagingSort);
		listaKomentara = pageKomentariFitnesPrograma.getContent();

		List<FitnesProgramKomentarOdgovor> komentari = listaKomentara.stream().map(this::mapirajKomentareUDTO)
				.collect(Collectors.toList());

		odgovor.put("komentari", komentari);
		odgovor.put("trenutnaStranica", pageKomentariFitnesPrograma.getNumber());
		odgovor.put("ukupnoElemenata", pageKomentariFitnesPrograma.getTotalElements());
		odgovor.put("brojStranica", pageKomentariFitnesPrograma.getTotalPages());
		return odgovor;
	}

	public void dodajKomentar(FitnesProgramKomentarZahtjev zahtjev) {
		FitnesProgram program = fitnesProgramRepository.findById(zahtjev.getFitnesProgramId())
				.orElseThrow(() -> new EntitetNijePronadjenException(
						"Fitnes program sa id-em " + zahtjev.getFitnesProgramId() + " nije pronađen."));
		Korisnik komentator = korisnikRepository.findByKorisnickoIme(zahtjev.getKorisnickoIme()).orElseThrow(
				() -> new EntitetNijePronadjenException("Korisnik " + zahtjev.getKorisnickoIme() + " nije pronađen."));
		FitnesProgramKomentar komentar = FitnesProgramKomentar.builder().komentar(zahtjev.getKomentar())
				.vrijeme(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString()).korisnik(komentator)
				.fitnesProgram(program).build();
		fitnesProgramKomentarRepository.save(komentar);
	}

}
