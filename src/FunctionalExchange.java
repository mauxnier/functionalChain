public class FunctionalExchange extends ChainElt {
	
	private Output output;
	private Input input;
	private FunctionalChainInvolvments_function source;
	private FunctionalChainInvolvments_function target;
	
	public FunctionalExchange(String id, String name, String summary) {
		super(id, name, summary);
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
}