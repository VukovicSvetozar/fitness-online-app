package vs.fitnessOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vs.fitnessOnline.entity.*;

public interface PorukaSavjetnikRepository extends JpaRepository<PorukaSavjetnik, Integer> {

	List<PorukaSavjetnik> findBySavjetnik(Savjetnik savjetnik);
	
}
