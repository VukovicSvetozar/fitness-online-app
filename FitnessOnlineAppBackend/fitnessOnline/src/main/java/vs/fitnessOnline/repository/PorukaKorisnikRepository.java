package vs.fitnessOnline.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.*;

public interface PorukaKorisnikRepository extends JpaRepository<PorukaKorisnik, Integer> {

	List<PorukaKorisnik> findByPosiljalacKorisnickoImeAndPrimalacKorisnickoIme(String posiljalacKorisnickoIme,
			String primalacKorisnickoIme);

}
