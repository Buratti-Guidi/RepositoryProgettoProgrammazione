package bg.Weather.exception;

import java.time.LocalDateTime;
import java.util.Date;

public class ErrorMessage {
	
	private String type;
	private LocalDateTime timestamp;
	private String message;

	public ErrorMessage() {
	}

	public ErrorMessage(String type,LocalDateTime timestamp, String message ) {
		this.timestamp = timestamp;
		this.message = message;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
