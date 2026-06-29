package vs.fitnessOnline.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogovanjeService {

	private static final Logger logger = LoggerFactory.getLogger(LogovanjeService.class);
	private static final String LOG_DIREKTORIJUM = "log/";

	public String vratiLogove(String logLevelInfo, String logLevelTrace, String logLevelDebug, String logLevelWarn,
			String logLevelError, String logLevelFatal, String startTime, String endTime) throws IOException {

		StringBuilder logSadrzaj = new StringBuilder();
		StringBuilder filtriraniSadrzaj = new StringBuilder();

		try (Stream<Path> fileStream = Files.list(Paths.get(LOG_DIREKTORIJUM))) {
			fileStream.filter(path -> path.getFileName().toString().matches(".*\\.log.*")).forEach(path -> {
				try {
					String ucitaniSadrzaj = Files.readString(path, StandardCharsets.UTF_8);
					logSadrzaj.append(ucitaniSadrzaj).append("\n");
				} catch (IOException e) {
					e.printStackTrace();
					String greska = "Greska pri citanju datoteka.";
					logger.error(greska);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			String greska = "Greska. Nije pronadjen direktorijum.";
			logger.error(greska);
			return greska;
		}
		try {
			String[] logLinije = logSadrzaj.toString().split(System.lineSeparator());
			for (String linija : logLinije) {
				if (!linija.contains("java.base")) {
					if (filtiranjePoruka(linija, logLevelInfo, logLevelTrace, logLevelDebug, logLevelWarn,
							logLevelError, logLevelFatal, startTime, endTime)) {
						filtriraniSadrzaj.append(linija).append(System.lineSeparator());
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("REST servis za pristup logovima je pozvan");
		return filtriraniSadrzaj.toString();
	}

	private boolean filtiranjePoruka(String logLinija, String logLevelInfo, String logLevelTrace, String logLevelDebug,
			String logLevelWarn, String logLevelError, String logLevelFatal, String startTime, String endTime) {

		if (logLinija.isBlank())
			return false;

		// Filtriranje po nivou logovanja.
		String logLevel = logLinija.split("#")[1].trim();
		String[] logLevels = { logLevelInfo, logLevelTrace, logLevelDebug, logLevelWarn, logLevelError, logLevelFatal };

		boolean prolazi = Arrays.stream(logLevels).allMatch(element -> element == null) || Arrays.stream(logLevels)
				.filter(element -> element != null).anyMatch(element -> element.equalsIgnoreCase(logLevel));

		// Filtriranje po vremenskom opsegu.
		if (prolazi) {
			if (startTime != null && endTime != null && !startTime.isEmpty() && !endTime.isEmpty()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
				String logDateTimeString = logLinija.split("#")[0].trim().replace(" ", "T");
				LocalDateTime logDateTime = LocalDateTime.parse(logDateTimeString, formatter);
				LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
				LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
				if (logDateTime.isBefore(startDateTime) || logDateTime.isAfter(endDateTime)) {
					prolazi = false;
				}
			}
		}
		return prolazi;
	}

}
