package vs.fitnessOnline.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.FitnesProgram;
import vs.fitnessOnline.entity.Korisnik;
import vs.fitnessOnline.entity.Ucestvovanje;

public interface UcestvovanjeRepository extends JpaRepository<Ucestvovanje, Integer> {

	Set<Ucestvovanje> findByKupac(Korisnik kupac);
	
	boolean existsByFitnesProgramAndKupac(FitnesProgram fitnesProgram, Korisnik kupac);

}
