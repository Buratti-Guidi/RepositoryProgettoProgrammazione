package bg.Weather.util.filter;

import bg.Weather.exception.UserErrorException;

/**
 * Filtro che rappresenta il simbolo matematico "non incluso"
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class NotincludedFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	/**
	 * Assegna il valore da verificare preso in input all'attributo della classe
	 * @param vrfValue valore da verificare preso in input
	 */
	public NotincludedFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() throws UserErrorException{
		try {
			if(super.getValue().size() != 2)
				throw new UserErrorException("The filter 'notIncluded' accepts only 2 numerical values");
			
			if(this.vrfValue > ((Number)super.getValue().get(0)).doubleValue() && this.vrfValue < ((Number)super.getValue().get(1)).doubleValue())
				return false;
			return true;
		} catch (ClassCastException e) {
			throw new UserErrorException("The filter 'notIncluded' accepts only numerical values");
		}
	}
}
