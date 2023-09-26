import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Function extends ChainElt {
	
	public Function(String id, String name) {
		super(id, name);
	}
	
	public Function(String id, String name, String summary) {
		super(id, name, summary);
	}
	
	public Output process() {
		printTrace();
		return createOutput();
	}
	
	public Output process(Input input) {
		printTrace();
		System.out.println("Input:" + input);
		return createOutput();
	}
	
	public List<Output> process(List<Input> inputs) {
		printTrace();
		
		/* Manage inputs */
		for (Input input : inputs) {
			System.out.println("Input:" + input);
		}
		
		/* Manage outputs */
		ArrayList<Output> outputs = new ArrayList<>();
		outputs.add(createOutput());
		return outputs;
	}
	
	private Output createOutput() {
		/* Manage output */
		String id = UUID.randomUUID().toString();
		return new Output(id, "o" + id, "output");
	}
	
	public void printTrace() {
		System.out.println("Process function " + getName() + " with id " + getId());
	}
}