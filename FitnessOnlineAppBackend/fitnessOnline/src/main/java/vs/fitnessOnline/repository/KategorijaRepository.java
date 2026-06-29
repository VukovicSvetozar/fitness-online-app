package vs.fitnessOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.Kategorija;

public interface KategorijaRepository extends JpaRepository<Kategorija, Integer> {
	
}
