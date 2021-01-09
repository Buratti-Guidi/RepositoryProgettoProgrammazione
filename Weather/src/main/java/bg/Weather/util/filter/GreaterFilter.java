package bg.Weather.util.filter;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

public class GreaterFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public GreaterFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		try {
			if(super.getValue().size() != 1)
				throw new InternalServerException("The filter 'greater' accepts only 1 numerical value");
			
			if(this.vrfValue > ((Number)super.getValue().firstElement()).doubleValue())
				return true;
			return false;
		} catch (ClassCastException e) {
			throw new UserErrorException("The filter 'greater' accepts only numerical values");
		}
	}
}
