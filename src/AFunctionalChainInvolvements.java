public abstract class AFunctionalChainInvolvements extends StorageMibField {
	
	private final String id;
	private final String involvedId; // id de Function ou id de FunctionalExchange
	
	public AFunctionalChainInvolvements(String id, String involvedId) {
		this.id = id;
		this.involvedId = involvedId;
	}
	
	@Override
	public String GetId() {
		return this.id;
	}
	
	public String getInvolvedId() {
		return involvedId;
	}
}
