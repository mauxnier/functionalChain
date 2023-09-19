public class FunctionalChainInvolvments_function extends ChainElt {
	
	private Function function;
	
	public FunctionalChainInvolvments_function(String id, String name, String summary) {
		super(id, name, summary);
	}
	
	public Function getFunction() {
		return function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}
}