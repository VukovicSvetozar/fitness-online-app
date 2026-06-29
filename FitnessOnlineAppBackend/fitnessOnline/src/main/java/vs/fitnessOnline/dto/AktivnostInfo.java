package vs.fitnessOnline.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AktivnostInfo {

	@NotNull
	@Positive
	private Integer ukupno;

	@NotNull
	@Positive
	private Integer brojZavrsenih;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	private String datum;

}
