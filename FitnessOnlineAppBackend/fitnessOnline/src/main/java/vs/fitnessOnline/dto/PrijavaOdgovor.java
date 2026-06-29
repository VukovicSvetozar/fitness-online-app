package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrijavaOdgovor {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	private String avatarUrl;
	
	@NotBlank
	private String jwtToken;
	
	@NotBlank
	private String refreshJwtToken;

}
