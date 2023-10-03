public class Input extends StorageMibField {
	private String id;
	private String name = "";
	
	Input(String id, String name) {
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
