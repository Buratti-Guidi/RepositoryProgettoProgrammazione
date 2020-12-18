package bg.Weather.model;

public class UserBox{

	protected double length; //lunghezza in km del box
	protected double width; //larghezza in km del box
	
	public UserBox() {}
	
	public UserBox(double length, double width) {
		this.length = length;
		this.width = width;
	}
	
	public double getLength() {
		return length;
	}
	
	public void setLength(double length) {
		this.length = length;
	}
	
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
}
