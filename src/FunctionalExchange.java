public class FunctionalExchange extends StorageMibField {
	
	private String id;
	private String name = "";
	private Output output;
	private Input input;
	private FunctionalChainInvolvments_function source;
	private FunctionalChainInvolvments_function target;
	
	public FunctionalExchange(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Output getOutput() {
		return output;
	}
	
	public void setOutput(Output output) {
		this.output = output;
	}
	
	public Input getInput() {
		return input;
	}
	
	public void setInput(Input input) {
		this.input = input;
	}
	
	public FunctionalChainInvolvments_function getSource() {
		return source;
	}
	
	public void setSource(FunctionalChainInvolvments_function source) {
		this.source = source;
	}
	
	public FunctionalChainInvolvments_function getTarget() {
		return target;
	}
	
	public void setTarget(FunctionalChainInvolvments_function target) {
		this.target = target;
	}
	
	@Override
	public String GetId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
}