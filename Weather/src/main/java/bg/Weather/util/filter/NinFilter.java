package bg.Weather.util.filter;

public class NinFilter extends WeatherFilter implements Filter{

	private double vrfValue;
	
	public NinFilter(double vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		for(Double d : super.getValue()) {
			if(this.vrfValue == d)
				return false;
		}
		return true;
	}
}
