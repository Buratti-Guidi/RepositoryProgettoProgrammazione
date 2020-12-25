package bg.Weather.util.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WeatherFilter {

	protected String filter;
	protected double value;
	
	private WeatherFilter wf;
	
	public WeatherFilter() {}

	public WeatherFilter(String filter, double value) {
		this.filter = filter;
		this.value = value;
	}

	public void verifyFilter(){
		switch (this.getFilter()) {

		case "$gt":
			wf = new GreaterFilter();
			break;
			
		//case "$gte":
			
		default: throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This filter doesn't exist");
		}
	}
	
	public boolean getResponse(double vrfValue) {
		return ((Filter)wf).response(vrfValue);
	}

	public String getFilter() {
		return this.filter;
	}
	
	public double getValue() {
		return this.value;
	}

}
