package bg.Weather.util.filter;

import bg.Weather.exception.FilterErrorException;

/**
 * Filtro che rappresenta il simbolo matematico "incluso"
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class IncludedFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	/**
	 * Assegna il valore da verificare preso in input all'attributo della classe
	 * @param vrfValue valore da verificare preso in input
	 */
	public IncludedFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() throws FilterErrorException{
		try {
			if(super.getValue().size() != 2)
				throw new FilterErrorException("The filter 'included' accepts only 2 numerical values");
			
			if(this.vrfValue > ((Number)super.getValue().get(0)).doubleValue() && this.vrfValue < ((Number)super.getValue().get(1)).doubleValue())
				return true;
			return false;
		} catch (ClassCastException e) {
			throw new FilterErrorException("The filter 'included' accepts only numerical values");
		}
	}
}
