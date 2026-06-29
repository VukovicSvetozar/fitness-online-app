package vs.fitnessOnline.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.service.LogovanjeService;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/logovi")
@RequiredArgsConstructor
public class LogovanjeController {

	private final LogovanjeService logovanjeService;

	@GetMapping("")
	public ResponseEntity<String> vratiLogove(@RequestParam(required = false) String logLevelInfo,
			@RequestParam(required = false) String logLevelTrace, @RequestParam(required = false) String logLevelDebug,
			@RequestParam(required = false) String logLevelWarn, @RequestParam(required = false) String logLevelError,
			@RequestParam(required = false) String logLevelFatal, @RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime) throws IOException {

		String logovi = logovanjeService.vratiLogove(logLevelInfo, logLevelTrace, logLevelDebug, logLevelWarn,
				logLevelError, logLevelFatal, startTime, endTime);

		if (logovi.startsWith("Greska"))
			return new ResponseEntity<>(logovi, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(logovi, HttpStatus.OK);
	}

}
