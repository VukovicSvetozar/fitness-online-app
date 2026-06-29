package vs.fitnessOnline.entity;

import java.io.Serializable;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class FitnesProgramSpecifikacijaId implements Serializable {

	private static final long serialVersionUID = -2343536857390097412L;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "fitnes_program_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private FitnesProgram fitnesProgram;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "specifikacija_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Specifikacija specifikacija;

}