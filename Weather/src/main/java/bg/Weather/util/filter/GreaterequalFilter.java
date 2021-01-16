package bg.Weather.util.filter;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

/**
 * Filtro che rappresenta il simbolo matematico "maggiore o uguale"
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class GreaterequalFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	/**
	 * Assegna il valore da verificare preso in input all'attributo della classe
	 * @param vrfValue valore da verificare preso in input
	 */
	public GreaterequalFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		try {
			if(super.getValue().size() != 1)
				throw new InternalServerException("The filter 'greaterEqual' accepts only 1 numerical value");
			
			if(this.vrfValue >= ((Number)super.getValue().firstElement()).doubleValue())
				return true;
			return false;
		} catch (ClassCastException e) {
			throw new UserErrorException("The filter 'greaterEqual' accepts only numerical values");
		}
	}
}
