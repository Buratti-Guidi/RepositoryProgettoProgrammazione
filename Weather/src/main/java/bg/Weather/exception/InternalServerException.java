package bg.Weather.exception;

/**
 *  Classe che rappresenta una eccezione personalizzata di tipo generale
 * @author Luca Guidi
 * @author Christopher Buratti
 *
 */
public class InternalServerException extends Exception{

	public InternalServerException() {
		super();
	}
	
	public InternalServerException(String message) {
		super(message);
	}
}