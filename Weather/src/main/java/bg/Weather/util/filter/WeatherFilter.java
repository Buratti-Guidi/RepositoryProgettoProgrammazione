package bg.Weather.util.filter;

public class WeatherFilter {

	protected String filter;
	protected WeatherFilter wf;

	public WeatherFilter(String filter) {
		this.filter = filter;
	}

	public void verifyFilter() throws Exception {
		switch (this.getFilter()) {

		case "$gt":
			// this.wf = new greaterFilter;

		case "$gte":
		}
	}

	public String getFilter() {
		return this.filter;
	}

}
