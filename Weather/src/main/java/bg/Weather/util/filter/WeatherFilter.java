package bg.Weather.util.filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

public class WeatherFilter {

	protected String filter;
	protected Vector<Double> value;
	
	public WeatherFilter() {
		value = new Vector<Double>();
	}

	public WeatherFilter(String filter, Vector<Double> values) {
		this.filter = filter;
		value = new Vector<Double>();
		for(int i = 0; i < values.size(); i++)
			this.value.add(values.get(i));
	}

	public boolean getResponse(double vfrValue){
		Filter f;
		
		try {
			String className = "bg.Weather.util.filter." + this.getFilter().substring(0,1).toUpperCase() + this.getFilter().substring(1, this.getFilter().length()).toLowerCase() + "Filter";
			Class<?> cls = Class.forName(className);
			Constructor<?> ct = cls.getDeclaredConstructor(double.class);
			f = (Filter)ct.newInstance(vfrValue);
			
			return f.response();
		} catch(ClassNotFoundException e) {
			throw new UserErrorException("This filter doesn't exist");
		}
		catch(InvocationTargetException e) {
			throw new InternalServerException("Error in getStats");
		}
		catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			throw new InternalServerException("General error, try later...");
		}
	}

	public String getFilter() {
		return this.filter;
	}
	
	public Vector<Double> getValue() {
		return this.value;
	}
}
