package bg.Weather.util.filter;

import bg.Weather.exception.UserErrorException;

public class NinFilter extends WeatherFilter implements Filter{

	private String vrfValue;
	
	public NinFilter(String vrfValue) {
		this.vrfValue = vrfValue.toUpperCase();
	}
	
	public boolean response() {
		try {
			for(Object o : super.getValue()) {
				if(this.vrfValue.equals(((String)o).toUpperCase()))
					return false;
			}
			return true;
		} catch(ClassCastException e) {
			throw new UserErrorException("The filter 'nin' accepts only strings");
		}
	}
}
