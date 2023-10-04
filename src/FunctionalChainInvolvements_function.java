public class FunctionalChainInvolvements_function extends AFunctionalChainInvolvements {
	
	private Function function;
	
	public FunctionalChainInvolvements_function(String id, String involvedId) {
		super(id, involvedId);
	}
	
	public Function getFunction() {
		return function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}
}