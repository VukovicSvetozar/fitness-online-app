package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutentifikacijaSavjetnikOdgovor {

	@NotBlank
	private String jwtToken;

}
