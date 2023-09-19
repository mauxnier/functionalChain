import java.util.ArrayList;

public class FunctionalChain extends ChainElt {
	
	private ArrayList<FunctionalChainInvolvments_function> functions = new ArrayList<>();
	
	public FunctionalChain(String id, String name) {
		super(id, name);
	}
	
	public FunctionalChain(String id, String name, String summary) {
		super(id, name, summary);
	}
	
	public void addFunction(Function function, String id, String name, String summary) {
		FunctionalChainInvolvments_function fcif = new FunctionalChainInvolvments_function(id, name, summary);
		fcif.setFunction(function);
		functions.add(fcif);
	}
	
	/**
	 * Start le process f1 vers fn
	 * @return result
	 */
	public String start() {
		for (FunctionalChainInvolvments_function fcif : functions) {
			//TODO appeler fonction et faire swtich fonction
		}
	}
}