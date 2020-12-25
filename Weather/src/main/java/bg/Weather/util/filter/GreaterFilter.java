package bg.Weather.util.filter;

public class GreaterFilter extends WeatherFilter implements Filter{

	public boolean response(double vrfValue) {
		if(vrfValue > super.getValue())
			return true;
		return false;
	}
}
