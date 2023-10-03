public class FunctionalChainInvolvements_exchange extends FunctionalChainInvolvements {
	private FunctionalExchange exchange;
	
	public FunctionalChainInvolvements_exchange(String id) {
		super(id);
	}
	
	public FunctionalExchange getExchange() {
		return exchange;
	}
	
	public void setExchange(FunctionalExchange exchange) {
		this.exchange = exchange;
	}
}