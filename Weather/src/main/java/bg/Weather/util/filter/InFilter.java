package bg.Weather.util.filter;

import bg.Weather.exception.UserErrorException;

public class InFilter extends WeatherFilter implements Filter{

	private String vrfValue;
	
	public InFilter(String vrfValue) {
		this.vrfValue = vrfValue.toUpperCase();
	}
	
	public boolean response() throws UserErrorException {
		try {
			for(Object o : super.getValue()) {
				if(this.vrfValue.equals(((String)o).toUpperCase()))
					return true;
			}
			return false;
		} catch(ClassCastException e) {
			throw new UserErrorException("The filter 'in' accepts only strings");
		}
	}
}
