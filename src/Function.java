import java.util.ArrayList;

public class Function extends StorageMibField {
	String ownedFunctionsid;
	String ownedFunctionsName = "";
	ArrayList<Output> outputs;
	ArrayList<Input> inputs;
	
	Function(String p_Functionsid, String p_FunctionsName) {
		ownedFunctionsid = p_Functionsid;
		ownedFunctionsName = p_FunctionsName;
		
		outputs = new ArrayList<Output>();
		inputs = new ArrayList<Input>();
	}
	
	public void addOutput(Output e) {
		outputs.add(e);
	}
	
	public void addInput(Input e) {
		inputs.add(e);
	}
	
	public String GetId() {
		return ownedFunctionsid;
	}
	
	public String getName() {
		return this.ownedFunctionsName;
	}
}