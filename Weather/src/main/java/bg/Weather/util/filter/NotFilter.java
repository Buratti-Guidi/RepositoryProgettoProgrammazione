package bg.Weather.util.filter;

import bg.Weather.exception.UserErrorException;

/**
 * Filtro che rappresenta il simbolo matematico "diverso da"
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class NotFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	/**
	 * Assegna il valore da verificare preso in input all'attributo della classe
	 * @param vrfValue valore da verificare preso in input
	 */
	public NotFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() throws UserErrorException {
		try {
			if(super.getValue().size() != 1)
				throw new UserErrorException("The filter 'not' accepts only 1 numerical value");
			
			if(this.vrfValue != ((Number)super.getValue().firstElement()).doubleValue())
				return true;
			return false;
		} catch(ClassCastException e) {
			throw new UserErrorException("The filter 'not' accepts only numerical values");
		}
	}
}
