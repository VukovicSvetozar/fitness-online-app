package vs.fitnessOnline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredlogVjezbeOdgovor {

	private String name;
	
	private String difficulty;
	
	private String instructions;
	
}
