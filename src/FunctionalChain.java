import java.util.ArrayList;
import java.util.UUID;

public class FunctionalChain extends StorageMibField {
	
	private final String id;
	private String name = "";
	private String summary;
	
	private final ArrayList<FunctionalChainInvolvements_exchange> chainInvolvments = new ArrayList<>();
	
	public FunctionalChain(String id, String name, String summary) {
		this.id = id;
		this.name = name;
		this.summary = summary;
	}
	
//	public FunctionalChainInvolvements_exchange addFunctionalExchange(FunctionalExchange exchange, FunctionalChainInvolvements_function source, FunctionalChainInvolvements_function target) {
//		FunctionalChainInvolvements_exchange fcie = new FunctionalChainInvolvements_exchange(UUID.randomUUID().toString());
//		exchange.setSource(source);
//		exchange.setTarget(target);
//		fcie.setExchange(exchange);
//		chainInvolvments.add(fcie);
//		return fcie;
//	}
	
	/**
	 * Start le process f1 vers fn
	 */
//	public void start() {
//		for (FunctionalChainInvolvements_exchange fcie : chainInvolvments) {
//			FunctionalExchange fe = fcie.getExchange();
//			System.out.println("Source : " + fe.getSource().getFunction().getName());
//			System.out.println("Output : " + fe.getOutput().getName());
//			System.out.println("Input : " + fe.getInput().getName());
//			System.out.println("Target : " + fe.getTarget().getFunction().getName());
//		}
//	}
	
	@Override
	public String GetId() {
		return this.id;
	}
}