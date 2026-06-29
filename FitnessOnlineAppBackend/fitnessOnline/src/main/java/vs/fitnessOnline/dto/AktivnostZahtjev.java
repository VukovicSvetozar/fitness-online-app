package vs.fitnessOnline.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AktivnostZahtjev {

	@NotBlank
	@Size(min = 3, max = 30)
	@Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
	private String naziv;

	@NotNull
	private Integer kategorijaId;

	@NotBlank
	private String intezitet;

	@NotNull
	@Positive
	private Integer trajanje;

	@NotNull
	@Positive
	private Integer brojPonavljanja;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

}
