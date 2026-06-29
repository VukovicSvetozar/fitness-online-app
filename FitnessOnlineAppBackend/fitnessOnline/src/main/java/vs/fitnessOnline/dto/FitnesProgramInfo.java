package vs.fitnessOnline.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FitnesProgramInfo {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@NotBlank
	@Size(min = 3, max = 30)
	private String naziv;

	@Positive
	@Digits(integer = 10, fraction = 2)
	@NotNull
	private Double cijena;

	@NotBlank
	@Size(min = 3, max = 30)
	private String nivoTezine;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 30)
	private String tipLokacije;

	@Override
	public String toString() {
		return "FitnesProgram" + "\n" + "\tInstruktor: " + korisnickoIme + "\n" + "\tNaziv: " + naziv + "\n"
				+ "\tCijena: " + cijena + "km" + "\n" + "\tTezina: " + nivoTezine + "\n" + "\tLokacija: " + tipLokacije
				+ "\n";
	}

}
