package bg.Weather.util.operator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

public class WeatherOperator {

	protected Vector<Vector<Boolean>> conditions;
	
	public WeatherOperator() {
		conditions = new Vector<Vector<Boolean>>();
	}
	
	public void addCondition(Boolean condition, int index) {
		if(conditions.size() == index)
			conditions.add(index, new Vector<Boolean>());
		conditions.get(index).add(condition);
	}
	
	public Operator getOperator(String opName) throws UserErrorException,InternalServerException{
		String className = "bg.Weather.util.operator." + opName.substring(0,1).toUpperCase() + opName.substring(1, opName.length()).toLowerCase() + "Operator";
		Operator o;
		try {
			Class<?> cls = Class.forName(className);
			Constructor<?> ct = cls.getDeclaredConstructor();
			o = (Operator)ct.newInstance();
			
		} catch (ClassNotFoundException e) {
			throw new UserErrorException("This operator doesn't exist");
		}
		catch(InvocationTargetException ex) {
			throw new InternalServerException("Error in getOperator");
		}
		catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | UserErrorException exe) {
			throw new InternalServerException("General error, try later...");
		}
		return o;
	}
}
