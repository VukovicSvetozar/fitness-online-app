package vs.fitnessOnline.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.FitnesProgramInfo;
import vs.fitnessOnline.entity.FitnesProgram;
import vs.fitnessOnline.entity.Korisnik;
import vs.fitnessOnline.exception.EntitetNijePronadjenException;
import vs.fitnessOnline.repository.FitnesProgramRepository;
import vs.fitnessOnline.repository.KategorijaRepository;
import vs.fitnessOnline.repository.KorisnikRepository;

@Service
@RequiredArgsConstructor
public class PretplataService {

	private final KorisnikRepository korisnikRepository;
	private final KategorijaRepository kategorijaRepository;
	private final MailService mailService;
	private final FitnesProgramRepository fitnesProgramRepository;
	private final ModelMapper modelMapper;

	public boolean pretplatiKorisnika(String korisnickoIme, Integer kategorijaId) {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme)
				.orElseThrow(() -> new EntitetNijePronadjenException("Korisnik nije pronađen"));
		if (korisnik.getPretplata() != 0)
			return false;
		if (!kategorijaRepository.existsById(kategorijaId)) {
			throw new EntitetNijePronadjenException("Kategorija sa id-em " + kategorijaId + " nije pronađena.");
		}
		korisnik.setPretplata(kategorijaId);
		korisnikRepository.saveAndFlush(korisnik);
		return true;

	}

	@Scheduled(cron = "0 58 23 * * ?")
	public void saljiMailPretplatnicima() {
		List<Korisnik> pretplatnici = korisnikRepository.findByPretplataGreaterThan(0);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatiranDatum = LocalDateTime.now().format(format);
		List<FitnesProgram> danasnjiProgrami = fitnesProgramRepository.findByVrijemeObjave(formatiranDatum);
		pretplatnici.forEach(pretplatnik -> {
			List<FitnesProgramInfo> danasnjiProgramiInfo = danasnjiProgrami.stream().filter(
					program -> !pretplatnik.getKorisnickoIme().equals(program.getInstruktor().getKorisnickoIme()))
					.filter(program -> pretplatnik.getPretplata() == program.getKategorija().getId())
					.map(this::mapirajFitnesProgramUDTO).collect(Collectors.toList());
			String poruka = "";
			if (danasnjiProgramiInfo.isEmpty())
				poruka = "Danas nema novih programa za pretplaćenu kategoriju";
			else
				poruka = danasnjiProgramiInfo.stream().map(info -> info.toString()).collect(Collectors.joining("\n"));
			mailService.posaljiInformacijeODanasnjimProgramima(pretplatnik.getEmail(), poruka);
		});
	}

	private FitnesProgramInfo mapirajFitnesProgramUDTO(FitnesProgram fitnesProgram) {
		FitnesProgramInfo dto = modelMapper.map(fitnesProgram, FitnesProgramInfo.class);
		dto.setKorisnickoIme(fitnesProgram.getInstruktor().getKorisnickoIme());
		return dto;
	}

}
