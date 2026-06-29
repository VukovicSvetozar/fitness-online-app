package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AktivacijaZahtjev {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String kljuc;

}
