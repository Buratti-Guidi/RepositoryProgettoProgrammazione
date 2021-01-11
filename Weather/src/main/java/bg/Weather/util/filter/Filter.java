package bg.Weather.util.filter;

/**
 * Interfaccia di un filtro generico
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public interface Filter {

	/**
	 * Controlla se il valore da verificare rispetta le condizioni del filtro richiesto
	 * @return "true" se l'oggetto soddisfa le richieste del filtro, "false" altrimenti
	 */
	public boolean response();
}
