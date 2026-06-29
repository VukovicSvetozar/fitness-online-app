package vs.fitnessOnline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.Savjetnik;

public interface SavjetnikRepository extends JpaRepository<Savjetnik, Integer> {

	Optional<Savjetnik> findByKorisnickoIme(String korisnickoIme);

	boolean existsByKorisnickoIme(String korisnickoIme);

}
