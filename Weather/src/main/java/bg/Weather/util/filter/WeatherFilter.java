package bg.Weather.util.filter;

public class WeatherFilter {

	protected String filter;
	protected double value;
	
	private WeatherFilter wf;
	
	public WeatherFilter() {}

	public WeatherFilter(String filter, double value) {
		this.filter = filter;
		this.value = value;
	}

	public void verifyFilter() throws Exception {
		switch (this.getFilter()) {

		case "$gt":
			wf = new GreaterFilter();
			break;
			
		//case "$gte":
			
		default: throw new Exception();
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
