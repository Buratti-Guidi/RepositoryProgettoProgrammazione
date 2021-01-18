package bg.Weather.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe che gestisce gli errori lanciati dal Controller
 * @author Luca Guidi
 * @author Christopher Buratti
 */
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	/**
	 * Gestisce le eccezioni UserErrorException, InternalServerException e FilterErrorException lanciate dal controller
	 * @param ex Eccezione da gestire
	 * @param request 
	 * @return Risposta che contiene il tipo di eccezione,il messaggio e l' ora 
	 */
	@ExceptionHandler(value = { UserErrorException.class, InternalServerException.class, FilterErrorException.class })
	public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
		
		String errorMessageDescription = ex.getLocalizedMessage();
		if(errorMessageDescription == null) errorMessageDescription = ex.toString();
		
		ErrorMessage errorMessage = new ErrorMessage(ex.getClass().getCanonicalName(),LocalDateTime.now(),errorMessageDescription);
		
		return new ResponseEntity<>(
				errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
