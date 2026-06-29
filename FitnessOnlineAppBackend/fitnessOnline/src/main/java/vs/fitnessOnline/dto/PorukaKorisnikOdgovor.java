package vs.fitnessOnline.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PorukaKorisnikOdgovor {

	@NotBlank
	@Size(min = 3, max = 80)
	private String tekstPoruke;
	
	@NotBlank
	private String vrijemePoruke;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoImePosiljalac;
	
}
