package vs.fitnessOnline.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlikaInfo {

	@NotBlank
	private String naziv;

	@NotBlank
	private String url;

}
