package domain;

public class AttributeInfo {
	
	private String attributeName;
	
	public AttributeInfo(String attributeName) {
		this.attributeName = attributeName;
	}
	
	@Override
	public String toString() {
		return this.attributeName;
	}
	
	

}
