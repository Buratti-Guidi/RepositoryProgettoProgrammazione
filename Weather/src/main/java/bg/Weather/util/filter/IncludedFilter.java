package bg.Weather.util.filter;

import bg.Weather.exception.InternalServerException;

public class IncludedFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public IncludedFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		if(super.getValue().size() != 2)
			throw new InternalServerException("Number of values wrong in IncludedFilter");
		
		if(this.vrfValue > super.getValue().get(0) && this.vrfValue < super.getValue().get(1))
			return true;
		return false;
	}
}
