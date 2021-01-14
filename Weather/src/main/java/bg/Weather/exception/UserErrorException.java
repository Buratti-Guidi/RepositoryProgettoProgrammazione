package bg.Weather.exception;

/**
 * Classe che rappresenta una eccezione personalizzata per gli errori commessi dall' utente
 * @author Luca Guidi
 * @author Christopher Buratti
 */
public class UserErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserErrorException() {
		super();
	}

	/**
	 * Imposta il messaggio dell' eccezione
	 * @param message messaggio dell' eccezione
	 */
	public UserErrorException(String message) {
		super(message);
	}
}
