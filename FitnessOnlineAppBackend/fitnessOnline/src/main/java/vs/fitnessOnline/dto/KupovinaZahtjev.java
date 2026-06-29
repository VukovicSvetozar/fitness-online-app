package vs.fitnessOnline.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KupovinaZahtjev {

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 5, max = 30)
	private String brojKartice;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 5, max = 30)
	private String payPalRacun;

	private boolean licno;

	@NotNull
	private Integer fitnesProgramId;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoImeKupca;

}
