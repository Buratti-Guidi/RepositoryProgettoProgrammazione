package bg.Weather.exception;

/**
 * Classe che rappresenta una eccezione personalizzata di tipo
 * RuntimeException
 * 
 * @author Luca Guidi
 * @author Christopher Buratti
 *
 */
public class UserErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserErrorException() {
		super();
	}

	public UserErrorException(String message) {
		super(message);
	}
}
