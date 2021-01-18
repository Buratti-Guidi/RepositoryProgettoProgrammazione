/**
 * 
 */
package bg.Weather.exception;

/**
 * Classe che rappresenta una eccezione personalizzata per gli errori sui filtri
 * @author Luca Guidi
 * @author Christopher Buratti
 */
public class FilterErrorException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FilterErrorException() {
		super();
	}
	
	/**
	 * Imposta il messaggio dell' eccezione
	 * @param message messaggio dell' eccezione
	 */
	public FilterErrorException(String message) {
		super(message);
	}
}
