package bg.Weather.util.filter;

public class UserFilter1 {

	private String param;
	private int days;
	
	public UserFilter1(String param, int days) {
		this.param = param;
		this.days = days;
	}
	
	public void verify() {
		FilterImpl1 fil = new FilterImpl1();
		fil.verifyParam(this.param, this.days);
	}
}
