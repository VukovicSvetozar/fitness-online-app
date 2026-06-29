package vs.fitnessOnline.dto;

import java.util.*;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FitnesProgramDetaljiOdgovor {

	private String avatarUrl;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@NotNull
	@PastOrPresent
	private String datumPristupa;

	@NotBlank
	@Size(max = 30)
	private String grad;

	@NotBlank
	@Email
	@Size(max = 100)
	private String email;

	@Pattern(regexp = "^[- +()0-9]+$")
	private String brojTelefona;

	@NotNull
	@PastOrPresent
	private String vrijemeObjave;

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
	private int trajanje;

	private String status;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 30)
	private String tipLokacije;

	@Size(min = 3, max = 100)
	private String adresaLokacije;

	@URL
	private String urlLokacije;

	@NotBlank
	private String nazivKategorije;

	private Map<String, String> specificneOsobine;

	private List<String> slikeFitnesProgramaUrl;

}
