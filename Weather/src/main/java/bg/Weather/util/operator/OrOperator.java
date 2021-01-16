package bg.Weather.util.operator;

/**
 * Operatore che rappresenta l'and logico
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class OrOperator extends WeatherOperator implements Operator{

	/**
	 * Costruttore vuoto
	 */
	public OrOperator() {}
	
	public boolean getResponse(int index) {
		for(Boolean b : conditions.get(index)) {
			if(b.booleanValue() == true)
				return true;
		}
		return false;
	}
}
