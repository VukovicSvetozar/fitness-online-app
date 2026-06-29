package vs.fitnessOnline.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.PredlogVjezbeOdgovor;

@Service
@RequiredArgsConstructor
public class PredloziService {

	@Value("${application.exercise.api.url}")
	private String vjezbeUrl;

	@Value("${application.exercise.api.key}")
	private String vjezbeApiKljuc;

	public Set<PredlogVjezbeOdgovor> vratiPredloge() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().add("X-Api-Key", vjezbeApiKljuc);
			return execution.execute(request, body);
		});
		PredlogVjezbeOdgovor[] vjezbeNiz = restTemplate.getForObject(vjezbeUrl, PredlogVjezbeOdgovor[].class);
		return new HashSet<>(Arrays.asList(vjezbeNiz));
	}

}
