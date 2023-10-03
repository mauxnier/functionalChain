public class FunctionalExchange extends StorageMibField {
	
	private final String id;
	private final String outputId;
	private final String inputId;
	private String name = "";
	private Output output;
	private Input input;
	
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