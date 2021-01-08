package bg.Weather.util.filter;

import bg.Weather.exception.InternalServerException;

public class GreaterFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public GreaterFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		if(super.getValue().size() != 1)
			throw new InternalServerException("Number of values wrong in GreaterFilter");
		
		if(this.vrfValue > ((Number)super.getValue().firstElement()).doubleValue())
			return true;
		return false;
	}
}
