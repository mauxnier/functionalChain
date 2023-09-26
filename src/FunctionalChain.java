import java.util.ArrayList;
import java.util.UUID;

public class FunctionalChain extends ChainElt {
	
	private final ArrayList<FunctionalChainInvolvments_exchange> chainInvolvments = new ArrayList<>();
	
	public FunctionalChain(String id, String name) {
		super(id, name);
	}
	
	public FunctionalChain(String id, String name, String summary) {
		super(id, name, summary);
	}
	
	public FunctionalChainInvolvments_exchange addFunctionalExchange(FunctionalExchange exchange, FunctionalChainInvolvments_function source, FunctionalChainInvolvments_function target) {
		FunctionalChainInvolvments_exchange fcie = new FunctionalChainInvolvments_exchange(UUID.randomUUID().toString(), "fcie_" + exchange.getName(), exchange.getSummary());
		exchange.setSource(source);
		exchange.setTarget(target);
		fcie.setExchange(exchange);
		chainInvolvments.add(fcie);
		return fcie;
	}
	
	/**
	 * Start le process f1 vers fn
	 */
	public void start() {
		for (FunctionalChainInvolvments_exchange fcie : chainInvolvments) {
			FunctionalExchange fe = fcie.getExchange();
			System.out.println("Source : " + fe.getSource().getFunction().getName());
			System.out.println("Output : " + fe.getOutput().getName());
			System.out.println("Input : " + fe.getInput().getName());
			System.out.println("Target : " + fe.getTarget().getFunction().getName());
		}
	}
}