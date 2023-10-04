public class FunctionalChainInvolvements_exchange extends AFunctionalChainInvolvements {
	private FunctionalExchange exchange;
	private final String sourceId; // id du précédent this
	private final String targetId; // id du prochain this
	private FunctionalChainInvolvements_function source;
	private FunctionalChainInvolvements_function target;
	
	public FunctionalChainInvolvements_exchange(String id, String involvedId, String sourceId, String targetId) {
		super(id, involvedId);
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	public FunctionalExchange getExchange() {
		return exchange;
	}
	
	public void setExchange(FunctionalExchange exchange) {
		this.exchange = exchange;
	}
	
	public String getSourceId() {
		return sourceId;
	}
	
	public String getTargetId() {
		return targetId;
	}
	
	public FunctionalChainInvolvements_function getSource() {
		return source;
	}
	
	public void setSource(FunctionalChainInvolvements_function source) {
		this.source = source;
	}
	
	public FunctionalChainInvolvements_function getTarget() {
		return target;
	}
	
	public void setTarget(FunctionalChainInvolvements_function target) {
		this.target = target;
	}
}