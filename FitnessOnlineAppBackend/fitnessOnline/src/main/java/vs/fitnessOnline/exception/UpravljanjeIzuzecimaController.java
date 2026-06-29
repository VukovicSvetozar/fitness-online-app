package vs.fitnessOnline.exception;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class UpravljanjeIzuzecimaController {

	@ExceptionHandler(EntitetNijePronadjenException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public GreskaPoruka entitetNijePronadjenException(EntitetNijePronadjenException ex, WebRequest zahtjev) {
		GreskaPoruka poruka = new GreskaPoruka(HttpStatus.NOT_FOUND.value(),
				LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), ex.getMessage(), zahtjev.getDescription(false));
		return poruka;
	}
	
	@ExceptionHandler(PogresanZahtjevException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GreskaPoruka pogresanZahtjevException(PogresanZahtjevException ex, WebRequest zahtjev) {
		GreskaPoruka poruka = new GreskaPoruka(HttpStatus.BAD_REQUEST.value(),
				LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), ex.getMessage(), zahtjev.getDescription(false));
		return poruka;
	}
	
	@ExceptionHandler(SukobImenaException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public GreskaPoruka sukobImenaException(SukobImenaException ex, WebRequest zahtjev) {
		GreskaPoruka poruka = new GreskaPoruka(HttpStatus.CONFLICT.value(),
				LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), ex.getMessage(), zahtjev.getDescription(false));
		return poruka;
	}
	
	@ExceptionHandler(NeispravniKredencijaliException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public GreskaPoruka neispravniKredencijali(NeispravniKredencijaliException ex, WebRequest zahtjev) {
		GreskaPoruka poruka = new GreskaPoruka(HttpStatus.UNAUTHORIZED.value(),
				LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), ex.getMessage(), zahtjev.getDescription(false));
		return poruka;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public GreskaPoruka opsteUpravljanjeIzuzecima(Exception ex, WebRequest zahtjev) {
		GreskaPoruka poruka = new GreskaPoruka(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), ex.getMessage(), zahtjev.getDescription(false));
		return poruka;
	}
	
}
