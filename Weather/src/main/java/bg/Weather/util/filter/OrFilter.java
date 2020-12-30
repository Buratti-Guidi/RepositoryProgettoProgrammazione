package bg.Weather.util.filter;

public class OrFilter extends OperatorFilter implements Operator{

	public OrFilter() {}
	
	public boolean getResponse(int index) {
		for(Boolean b : conditions.get(index)) {
			if(b.booleanValue() == true)
				return true;
		}
		return false;
	}
}
