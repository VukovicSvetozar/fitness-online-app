package vs.fitnessOnline.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OdgovorSavjetnika {

	private Integer id;
	
	@NotBlank
	@Size(min = 3, max = 30)
	private String naslov;
	
	@NotBlank
	@Size(min = 3, max = 300)
	private String tekst;
	
	@NotNull
	private String vrijeme;
	
	@NotBlank
	@Email
	@Size(max = 100)
	private String email;
	
	private MultipartFile prilog;

}
