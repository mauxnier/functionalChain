public class FunctionalChainInvolvments_function extends ChainElt implements IFunctionalChainInvolvments {
	
	private OwnedFunction function;
	
	public FunctionalChainInvolvments_function(String id, String name, String summary) {
		super(id, name, summary);
	}
	
	public OwnedFunction getFunction() {
		return function;
	}
	
	public void setFunction(OwnedFunction function) {
		this.function = function;
	}
}