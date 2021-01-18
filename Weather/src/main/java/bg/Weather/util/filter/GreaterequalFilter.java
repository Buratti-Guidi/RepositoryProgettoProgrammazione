package bg.Weather.util.filter;

import bg.Weather.exception.FilterErrorException;

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
	
	public boolean response() throws FilterErrorException{
		try {
			if(super.getValue().size() != 1)
				throw new FilterErrorException("The filter 'greaterEqual' accepts only 1 numerical value");
			
			if(this.vrfValue >= ((Number)super.getValue().firstElement()).doubleValue())
				return true;
			return false;
		} catch (ClassCastException e) {
			throw new FilterErrorException("The filter 'greaterEqual' accepts only numerical values");
		}
	}
}
