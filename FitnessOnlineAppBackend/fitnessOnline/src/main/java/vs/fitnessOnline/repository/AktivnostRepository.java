package vs.fitnessOnline.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.Aktivnost;
import vs.fitnessOnline.entity.Korisnik;

public interface AktivnostRepository extends JpaRepository<Aktivnost, Integer> {

	List<Aktivnost> findByKorisnikAndDatum(Korisnik korisnik, String datum);

	Set<Aktivnost> findByKorisnik(Korisnik korisnik);

}
