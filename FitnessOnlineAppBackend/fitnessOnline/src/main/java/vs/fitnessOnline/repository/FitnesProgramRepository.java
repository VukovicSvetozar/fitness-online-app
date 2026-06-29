package vs.fitnessOnline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.*;

public interface FitnesProgramRepository extends JpaRepository<FitnesProgram, Integer> {

	List<FitnesProgram> findByKategorija(Kategorija kategorija);

	Page<FitnesProgram> findByKategorija(Kategorija kategorija, Pageable pageable);

	List<FitnesProgram> findByVrijemeObjave(String vrijemeObjave);

	Page<FitnesProgram> findByUcestvovanje_KupacId(Integer kupacId, Pageable pageable);

	List<FitnesProgram> findByUcestvovanje_KupacId(Integer kupacId);

	List<FitnesProgram> findByInstruktor(Korisnik instruktor);

}
