package bg.Weather.util.filter;

public interface Operator {

	public boolean getResponse(int index); //ritorna true se la città rispetta le condizioni in base all'operatore "and" o "or" che sia, false altrimenti
	
	public void clearVector(); //resetta il vettore contenente i valori boolean che soddisfano le condizioni
	
	public void addCondition(Boolean condition, int index); //aggiunge "true" se la condizione è verificata, "false" altrimenti
}
