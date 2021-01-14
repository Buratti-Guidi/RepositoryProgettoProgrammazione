package bg.Weather.exception;

/**
 *  Classe che rappresenta una eccezione personalizzata di tipo generale
 * @author Luca Guidi
 * @author Christopher Buratti
 *
 */
public class InternalServerException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InternalServerException() {
		super();
	}
	
	/**
	 * Imposta il messaggio dell' eccezione
	 * @param message messaggio dell' eccezione
	 */
	public InternalServerException(String message) {
		super(message);
	}
}