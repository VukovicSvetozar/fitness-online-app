package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecifikacijaOdgovor {

	@NotNull
	private Integer id;

	@NotBlank
	private String naziv;

}
