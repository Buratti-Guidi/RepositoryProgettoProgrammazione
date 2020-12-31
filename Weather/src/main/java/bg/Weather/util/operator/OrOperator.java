package bg.Weather.util.operator;

public class OrOperator extends OperatorImpl implements Operator{

	public OrOperator() {}
	
	public boolean getResponse(int index) {
		for(Boolean b : conditions.get(index)) {
			if(b.booleanValue() == true)
				return true;
		}
		return false;
	}
}
