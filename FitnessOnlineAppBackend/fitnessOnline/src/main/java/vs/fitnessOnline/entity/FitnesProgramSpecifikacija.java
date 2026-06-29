package vs.fitnessOnline.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fitnes_program_specifikacija")
public class FitnesProgramSpecifikacija {

	@EmbeddedId
	private FitnesProgramSpecifikacijaId id = new FitnesProgramSpecifikacijaId();

	@Column(name = "vrijednost")
	private String vrijednost;

}