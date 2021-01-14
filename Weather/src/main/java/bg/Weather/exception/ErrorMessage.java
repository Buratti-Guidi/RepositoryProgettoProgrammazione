package bg.Weather.exception;

import java.time.LocalDateTime;

/**
 * Modello di un messaggio di errore
 * @author Luca Guidi
 * @author Christopher Buratti
 */
public class ErrorMessage {
	
	private String type;
	private LocalDateTime timestamp;
	private String message;

	public ErrorMessage() {
	}

	/**
	 * Assegna i valori del tipo di errore, del messaggio e dell' ora 
	 * @param type nome del tipo di errore
	 * @param timestamp ora attuale dell' errore
	 * @param message messaggio dell' errore
	 */
	public ErrorMessage(String type,LocalDateTime timestamp, String message ) {
		this.timestamp = timestamp;
		this.message = message;
		this.type = type;
	}

	/**
	 * Ottieni il tipo dell' errore
	 * @return tipo dell' errore
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Imposta il tipo dell' errore
	 * @param type tipo dell' errore
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Ottieni l' ora in cui è occorso l' errore
	 * @return ora in cui è occorso l' errore
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Imposta l' ora in cui è occorso l' errore
	 * @param timestamp ora in cui è occorso l' errore
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Ottieni il messaggio di errore
	 * @return messaggio di errore
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Imposta il messaggio di errore
	 * @param message messaggio di errore
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
