package bg.Weather.util.filter;

public interface Operator {

	public boolean getResponse(int index);
	
	public void clearVector();
	
	public void addCondition(Boolean condition, int index);
}
