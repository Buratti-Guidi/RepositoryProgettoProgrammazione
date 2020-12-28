package bg.Weather.util.filter;

import bg.Weather.exception.InternalServerException;

public class LessFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public LessFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		if(super.getValue().size() != 1)
			throw new InternalServerException("Number of values wrong in LessFilter");
		
		if(this.vrfValue < super.getValue().firstElement().doubleValue())
			return true;
		return false;
	}
}
