public class FunctionalChainInvolvements_function extends AFunctionalChainInvolvements {
	
	private Function function;
	private String summary;
	
	public FunctionalChainInvolvements_function(String id, String involvedId, String summary) {
		super(id, involvedId);
		this.summary = summary;
	}
	
	public Function getFunction() {
		return function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}
	
	public String getSummary() {
		return summary;
	}
}