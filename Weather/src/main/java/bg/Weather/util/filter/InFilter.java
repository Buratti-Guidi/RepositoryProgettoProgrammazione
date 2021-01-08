package bg.Weather.util.filter;

import java.util.Vector;

import bg.Weather.exception.UserErrorException;

public class InFilter extends WeatherFilter implements Filter{

	private String vrfValue;
	
	public InFilter(String vrfValue) {
		this.vrfValue = vrfValue;
	}
	
	public boolean response() throws UserErrorException {
		try {
			for(Object o : super.getValue()) {
				if(this.vrfValue.equals((String)o))
					return true;
			}
			return false;
		}catch(ClassCastException e) {
			throw new UserErrorException("Il filtro 'in' accetta solo stringhe");
		}
	}
}
