package bg.Weather.util.operator;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

public interface Operator {

	public boolean getResponse(int index); //ritorna true se la città rispetta le condizioni in base all'operatore "and" o "or" che sia, false altrimenti
	
	public void addCondition(Boolean condition, int index); //aggiunge "true" se la condizione è verificata, "false" altrimenti
}
