package bg.Weather.model;

public class UserBox{

	private double lenght; //lunghezza in km del box
	private double width; //larghezza in km del box
	
	public UserBox() {}
	
	public UserBox(double lenght, double width) {
		this.lenght = lenght;
		this.width = width;
	}
	
	public double getLenght() {
		return lenght;
	}
	
	public void setLenght(double lenght) {
		this.lenght = lenght;
	}
	
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
}
