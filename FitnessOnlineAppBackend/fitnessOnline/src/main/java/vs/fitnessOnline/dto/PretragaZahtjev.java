package vs.fitnessOnline.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PretragaZahtjev {

	private Integer kategorijaId;

	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Size(min = 3, max = 100)
	private String korisnickoIme;

	@Size(min = 3, max = 30)
	private String naziv;

	@Size(min = 2, max = 50)
	private String tipLokacije;

	@Positive
	@Digits(integer = 10, fraction = 2)
	private Double minCijena;

	@Positive
	@Digits(integer = 10, fraction = 2)
	private Double maxCijena;

	private String datumOd;

	private String datumDo;

	private String nivoTezine;

	@Positive
	private Integer minTrajanje;

	@Positive
	private Integer maxTrajanje;

}
