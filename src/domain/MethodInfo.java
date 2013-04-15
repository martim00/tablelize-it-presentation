package domain;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
	
	private String methodName;
	private List<AttributeInfo> usedAttributes = new ArrayList<AttributeInfo>();

	public MethodInfo(String methodName) {
		this.methodName = methodName;
		
	}
	
	public String getName() {
		return methodName;
	}
	
	public List<AttributeInfo> getUsedAttributes() {
		return usedAttributes;
	}
}
