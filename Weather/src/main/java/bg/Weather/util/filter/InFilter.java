package bg.Weather.util.filter;

public class InFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public InFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		for(Double d : super.getValue()) {
			if(this.vrfValue == d)
				return true;
		}
		return false;
	}
}
