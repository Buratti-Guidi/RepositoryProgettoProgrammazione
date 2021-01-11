package bg.Weather.util.operator;


/**
 * Interfaccia di un operatore generico
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public interface Operator {

	/**
	 * Ottieni "true" se l'index-esima città rispetta i filtri sulla base dell'operatore richiesto
	 * @param index indice della città
	 * @return "true" se la città rispetta le condizioni in base all'operatore "and" o "or" che sia, "false" altrimenti
	 */
	public boolean getResponse(int index);
	
	/**
	 * Aggiunge la condizione alla città index-esima
	 * @param condition "true" se la condizione rispetta il filtro, "false" altrimenti
	 * @param index indice della città all'interno del Vector conditions
	 */
	public void addCondition(Boolean condition, int index); //aggiunge "true" se la condizione è verificata, "false" altrimenti
}
