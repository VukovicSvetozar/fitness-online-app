package vs.fitnessOnline.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import vs.fitnessOnline.entity.*;

public interface FitnesProgramKomentarRepository extends JpaRepository<FitnesProgramKomentar, Integer> {

	List<FitnesProgramKomentar> findByFitnesProgramId(Integer fitnesProgramId);

	Page<FitnesProgramKomentar> findByFitnesProgram(FitnesProgram fitnesProgram, Pageable pageable);

}
