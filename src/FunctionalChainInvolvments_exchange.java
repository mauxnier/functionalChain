public class FunctionalChainInvolvments_exchange extends ChainElt implements IFunctionalChainInvolvments {
	
	FunctionalExchange exchange;
	
	public FunctionalChainInvolvments_exchange(String id, String name, String summary) {
		super(id, name, summary);
	}
	
	public FunctionalExchange getExchange() {
		return exchange;
	}
	
	public void setExchange(FunctionalExchange exchange) {
		this.exchange = exchange;
	}
}