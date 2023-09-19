public abstract class ChainElt {
	private String id;
	private String name;
	private String summary;
	
	public ChainElt(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public ChainElt(String id, String name, String summary) {
		this.id = id;
		this.name = name;
		this.summary = summary;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
}