package vs.fitnessOnline.dto;

import lombok.*;

import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitnesProgramKomentarZahtjev {

	@NotBlank
	@Size(min = 3, max = 300)
	private String komentar;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_. ,!?]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@NotNull
	private Integer fitnesProgramId;

}
