package vs.fitnessOnline.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GreskaPoruka {

	private int statusKod;

	private LocalDateTime vrijeme;

	private String poruka;

	private String opis;

}
