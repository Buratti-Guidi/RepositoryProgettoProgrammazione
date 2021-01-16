package bg.Weather.util.operator;

/**
 * Operatore che rappresenta l'and logico
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class AndOperator extends WeatherOperator implements Operator{

	/**
	 * Costruttore vuoto
	 */
	public AndOperator() {}
	
	public boolean getResponse(int index) {
		for(Boolean b : conditions.get(index)) {
			if(b.booleanValue() == false)
				return false;
		}
		return true;
	}
}
