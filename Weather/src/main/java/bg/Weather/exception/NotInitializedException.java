package bg.Weather.exception;

public class NotInitializedException extends Exception{

	public NotInitializedException() {
		super("Non e' ancora stato inizializzato");
	}
}
