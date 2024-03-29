package domain;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
	
	private String methodName;
	private List<AttributeInfo> usedAttributes = new ArrayList<AttributeInfo>();
	private MethodDeclaration declaration;

	public MethodInfo(MethodDeclaration declaration) {
		this.declaration = declaration;
		this.methodName = declaration.getName();
	}
	
	public String getName() {
		return methodName;
	}
	
	public List<AttributeInfo> getUsedAttributes() {
		return usedAttributes;
	}
	
	private boolean containsParameterWithName(String name) {
		List<Parameter> parameters = declaration.getParameters();
		for (Parameter parameter : parameters) {
			if (parameter.getId().getName() == name) 
				return true;
		}
		return false;
	}
	
	private String getExpressionToFind(String attrName) {
		return this.containsParameterWithName(attrName) 
				? "this." + attrName : attrName;
	}
	
	public void extractAttributesInUse(List<AttributeInfo> attributes) {

		List<Statement> statements = declaration.getBody().getStmts();

		if (statements == null)
			return;

		for (Statement statement : statements) {

			for (AttributeInfo attr : attributes) {
				
				String statementAsString = statement.toString();
				// TODO: tratar parametros com mesmo nome de um atributo...
				//String expressionToFind = this.getExpressionToFind(attr.getName());  
				
				if (statementAsString.contains(attr.getName())) {
					this.usedAttributes.add(attr);
				}
			}
		}


	}

	public boolean useAttributes() {
		return !this.usedAttributes.isEmpty();
	}
}
