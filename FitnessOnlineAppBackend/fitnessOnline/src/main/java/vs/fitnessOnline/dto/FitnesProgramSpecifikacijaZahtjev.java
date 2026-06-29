package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitnesProgramSpecifikacijaZahtjev {

	@Size(max = 50)
	private String vrijednost;

	@NotNull
	private Integer fitnesProgramId;

	@NotNull
	private Integer specifikacijaId;

}
