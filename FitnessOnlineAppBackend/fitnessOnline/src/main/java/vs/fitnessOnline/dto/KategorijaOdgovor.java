package vs.fitnessOnline.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KategorijaOdgovor {

	@NotNull
	private Integer id;

	@NotBlank
	private String naziv;

}
