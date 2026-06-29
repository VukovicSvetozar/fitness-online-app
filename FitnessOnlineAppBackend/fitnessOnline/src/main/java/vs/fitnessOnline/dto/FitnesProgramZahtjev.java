package vs.fitnessOnline.dto;

import lombok.*;

import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitnesProgramZahtjev {

	@NotBlank
	@Size(min = 3, max = 30)
	private String naziv;

	@NotBlank
	@Size(min = 3, max = 300)
	private String opis;

	@Positive
	@Digits(integer = 10, fraction = 2)
	@NotNull
	private Double cijena;

	@NotBlank
	@Size(min = 3, max = 30)
	private String nivoTezine;

	@NotNull
	@Positive
	private Integer trajanje;

	@NotNull
	private Integer kategorijaId;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 30)
	private String tipLokacije;

	@Size(min = 3, max = 100)
	private String adresaLokacije;

	@URL
	private String urlLokacije;

}
