package vs.fitnessOnline.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.Specifikacija;

public interface SpecifikacijaRepository extends JpaRepository<Specifikacija, Integer> {

	Set<Specifikacija> findByKategorijaId(Integer kategorijaId);

}
