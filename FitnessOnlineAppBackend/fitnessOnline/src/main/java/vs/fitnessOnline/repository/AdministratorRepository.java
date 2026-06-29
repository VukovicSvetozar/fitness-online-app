package vs.fitnessOnline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	Optional<Administrator> findByKorisnickoIme(String korisnickoIme);

	boolean existsByKorisnickoIme(String korisnickoIme);

}
