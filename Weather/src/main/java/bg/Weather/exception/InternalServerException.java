package bg.Weather.exception;

/**
 *  Classe che rappresenta una eccezione personalizzata di tipo generale
 * @author Luca Guidi
 * @author Christopher Buratti
 *
 */
public class InternalServerException extends RuntimeException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5328568523499779731L;

	public InternalServerException() {
		super();
	}
	
	public InternalServerException(String message) {
		super(message);
	}
}