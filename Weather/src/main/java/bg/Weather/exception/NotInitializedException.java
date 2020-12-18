package bg.Weather.exception;
/**
 * Classe che rappresenta una eccezione personalizzata di tipo NullPOinterException
 * @author Luca Guidi
 * @author Christopher Buratti 
 *
 */
public class NotInitializedException extends NullPointerException{

	public NotInitializedException() {
		super();
	}
	
	public NotInitializedException(String message) {
		super(message);
	}
}
