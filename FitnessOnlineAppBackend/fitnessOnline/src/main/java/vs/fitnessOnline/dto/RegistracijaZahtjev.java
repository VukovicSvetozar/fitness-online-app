package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistracijaZahtjev {

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

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	@Size(min = 8, max = 20)
	private String lozinka;

	private String avatarUrl;

}
