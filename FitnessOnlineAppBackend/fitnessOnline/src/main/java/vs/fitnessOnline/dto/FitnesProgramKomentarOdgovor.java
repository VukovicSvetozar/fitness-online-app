package vs.fitnessOnline.dto;

import java.time.LocalDateTime;

import lombok.*;

import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitnesProgramKomentarOdgovor {

	@NotNull
	private Integer id;

	@NotBlank
	@Size(min = 3, max = 300)
	private String komentar;

	@NotNull
	@PastOrPresent
	private LocalDateTime vrijeme;

	private String avatarUrl;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@NotNull
	private Integer fitnesProgramId;

}
