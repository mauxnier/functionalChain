public class FunctionalExchange extends StorageMibField {
	
	private final String id;
	private final String outputId;
	private final String inputId;
	private String name = "";
	private Output output;
	private Input input;
	private FunctionalChainInvolvements_function source;
	private FunctionalChainInvolvements_function target;
	
	public FunctionalExchange(String id, String name, String outputId, String inputId) {
		this.id = id;
		this.name = name;
		this.outputId = outputId;
		this.inputId = inputId;
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
	
	public FunctionalChainInvolvements_function getSource() {
		return source;
	}
	
	public void setSource(FunctionalChainInvolvements_function source) {
		this.source = source;
	}
	
	public FunctionalChainInvolvements_function getTarget() {
		return target;
	}
	
	public void setTarget(FunctionalChainInvolvements_function target) {
		this.target = target;
	}
	
	@Override
	public String GetId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOutputId() {
		return outputId;
	}
	
	public String getInputId() {
		return inputId;
	}
}