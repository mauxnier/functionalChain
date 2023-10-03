public class Output extends StorageMibField {
	private final String id;
	private String name = "";
	
	Output(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String GetId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
