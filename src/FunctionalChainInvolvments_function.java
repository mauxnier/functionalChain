public class FunctionalChainInvolvments_function extends StorageMibField implements IFunctionalChainInvolvments {
	
	private final String id;
	private String name = "";
	private Function function;
	
	public FunctionalChainInvolvments_function(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Function getFunction() {
		return function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}
	
	@Override
	public String GetId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
}