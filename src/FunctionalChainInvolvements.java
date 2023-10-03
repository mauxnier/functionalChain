public class FunctionalChainInvolvements extends StorageMibField {
	
	private final String id;
	private final String involvedId; // id de Function ou id de FunctionalExchange
	private final String sourceId; // id du précédent this
	private final String targetId; // id du prochain this
	private FunctionalChainInvolvements_function source;
	private FunctionalChainInvolvements_function target;
	
	public FunctionalChainInvolvements(String id, String involvedId, String sourceId, String targetId) {
		this.id = id;
		this.involvedId = involvedId;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	@Override
	public String GetId() {
		return this.id;
	}
	
	public String getInvolvedId() {
		return involvedId;
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