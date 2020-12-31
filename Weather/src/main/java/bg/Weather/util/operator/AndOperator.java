package bg.Weather.util.operator;

public class AndOperator extends OperatorImpl implements Operator{

	public AndOperator() {}
	
	public boolean getResponse(int index) {
		for(Boolean b : conditions.get(index)) {
			if(b.booleanValue() == false)
				return false;
		}
		return true;
	}
}
