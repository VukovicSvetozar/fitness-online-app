package vs.fitnessOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.Korisnik;
import vs.fitnessOnline.entity.MasaKorisnika;

public interface MasaKorisnikaRepository extends JpaRepository<MasaKorisnika, Integer> {

	List<MasaKorisnika> findByKorisnik(Korisnik korisnik);

	boolean existsByDatumAndKorisnik(String datun, Korisnik korisnik);

}
