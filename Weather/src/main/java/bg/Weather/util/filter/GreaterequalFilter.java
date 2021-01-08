package bg.Weather.util.filter;

import bg.Weather.exception.InternalServerException;

public class GreaterequalFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public GreaterequalFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		if(super.getValue().size() != 1)
			throw new InternalServerException("Number of values wrong in GreaterEqualFilter");
		
		if(this.vrfValue >= ((Number)super.getValue().firstElement()).doubleValue())
			return true;
		return false;
	}
}
