package bg.Weather.exception;

/**
 *  Classe che rappresenta una eccezione personalizzata di tipo generale
 * @author Luca Guidi
 * @author Christopher Buratti
 *
 */
public class GeneralException extends Exception{

	public GeneralException() {
		super();
	}
	
	public GeneralException(String message) {
		super(message);
	}
}