public class FunctionalChainInvolvments_exchange extends StorageMibField implements IFunctionalChainInvolvments {
	private final String id;
	private String name = "";
	private FunctionalExchange exchange;
	
	public FunctionalChainInvolvments_exchange(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public FunctionalExchange getExchange() {
		return exchange;
	}
	
	public void setExchange(FunctionalExchange exchange) {
		this.exchange = exchange;
	}
	
	@Override
	public String GetId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
}