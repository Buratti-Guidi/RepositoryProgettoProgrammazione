package bg.Weather.util.filter;

public class AndFilter extends OperatorFilter implements Operator{

	public AndFilter() {}
	
	public boolean getResponse(int index) {
		for(Boolean b : super.conditions.get(index)) {
			if(b.booleanValue() == false)
				return false;
		}
		return true;
	}
}
