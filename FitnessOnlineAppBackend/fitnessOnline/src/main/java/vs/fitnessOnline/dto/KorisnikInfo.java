package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KorisnikInfo {

	@NotBlank
	@Size(min = 3, max = 30)
	private String ime;

	@NotBlank
	@Size(min = 3, max = 30)
	private String prezime;

	@NotBlank
	@Size(max = 30)
	private String grad;

	@NotBlank
	@Email
	@Size(max = 100)
	private String email;
	
	@Pattern(regexp = "^[0-9]+$")
	private String brojTelefona;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@Size(min = 0, max = 20)
	@Pattern(regexp = "^(|([a-zA-Z0-9]{8,20}))$")
	private String staraLozinka;

	@Size(min = 0, max = 20)
	@Pattern(regexp = "^(|([a-zA-Z0-9]{8,20}))$")
	private String novaLozinka;

	private String avatarUrl;

}
