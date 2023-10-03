import java.util.ArrayList;

public class Function extends StorageMibField {
	private final String id;
	private String name = "";
	private ArrayList<Output> outputs;
	private ArrayList<Input> inputs;
	
	Function(String id, String name) {
		this.id = id;
		this.name = name;
		this.outputs = new ArrayList<Output>();
		this.inputs = new ArrayList<Input>();
	}
	
	public void addOutput(Output e) {
		outputs.add(e);
	}
	
	public void addInput(Input e) {
		inputs.add(e);
	}
	
	public String GetId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}