package bg.Weather.util.filter;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

public class IncludedFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public IncludedFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		try {
			if(super.getValue().size() != 2)
				throw new InternalServerException("The filter 'included' accepts only 2 numerical values");
			
			if(this.vrfValue > ((Number)super.getValue().get(0)).doubleValue() && this.vrfValue < ((Number)super.getValue().get(1)).doubleValue())
				return true;
			return false;
		} catch (ClassCastException e) {
			throw new UserErrorException("The filter 'included' accepts only numerical values");
		}
	}
}
