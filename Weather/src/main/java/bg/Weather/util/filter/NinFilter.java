package bg.Weather.util.filter;

public class NinFilter extends WeatherFilter implements Filter{

	private String vrfValue;
	
	public NinFilter(String vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() {
		for(Object o : super.getValue()) {
			if(this.vrfValue.equals((String)o))
				return false;
		}
		return true;
	}
}
