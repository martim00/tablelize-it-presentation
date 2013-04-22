package domain;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClassSplitter {
	
	private List<MethodInfo> methods = new ArrayList<MethodInfo>();
	private List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();

	public void splitClass(String path) throws IOException {
		
		// creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream(path);

        CompilationUnit compilationUnit = null;
        try {
        	
            compilationUnit = JavaParser.parse(in);
            
        } catch (ParseException e) {
        	
			e.printStackTrace();
		} finally {
			
            in.close();
        }
        
        new AttributeVisitor(this).visit(compilationUnit, null);
        new MethodVisitor(this).visit(compilationUnit, null); 
        
        generateDotFile(path + ".dot");
		
	}
	
	void writeContentsToFile(String contents, String filename) {
		FileWriter output = null;
		BufferedWriter writer = null;
	    try {
	      output = new FileWriter(filename);
	      writer = new BufferedWriter(output);
	      writer.write(contents);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    } finally {
	      if (output != null) {
	        try {
	          writer.close();
	          output.close();
	        } catch (IOException e) {
	          // Ignore issues during closing
	        }
	      }
	    }	
	}
	
	private void generateDotFile(String filename) {
		
		String contents = new String();
		
		contents += "digraph class {\n";

		for (AttributeInfo attribute : attributes) {
			contents += attribute.getName();
			contents += ";\n";
		}

		for (MethodInfo method : methods) {
			contents += method.getName();
			contents += "[shape=box];\n";
			
			List<AttributeInfo> usedAttrs = method.getUsedAttributes();
			for (AttributeInfo attr : usedAttrs) {
				contents += method.getName();
				contents += "->";
				contents += attr.getName();
				contents += ";\n";
			}
		}
		
		contents += "}";
		
		writeContentsToFile(contents, filename);

	}

	public int getMethodCount() {
		return methods.size();
	}
	
	public int getAttributeCount() {
		return this.attributes.size();
	}
	
	public List<AttributeInfo> getAttributes() {
		return this.attributes;
	}
	
	public MethodInfo getMethodInfoFor(String methodName) {
		for (MethodInfo methodInfo : methods) {
			if (methodInfo.getName().equals(methodName))
				return methodInfo;
		}
		return null;
	}
	
	
	private static class AttributeVisitor extends VoidVisitorAdapter<Object> {
		
		private ClassSplitter splitter = null;
    	
    	public AttributeVisitor(ClassSplitter splitter) {
    		this.splitter = splitter;
    	}
    	
		@Override
        public void visit(FieldDeclaration n, Object arg) {
			// TODO: tratar mais de uma variavel
			VariableDeclarator variable = n.getVariables().get(0);
			String var = variable.getId().getName();
			
			this.splitter.attributes.add(new AttributeInfo(var));
        }	
	}
	
    private static class MethodVisitor extends VoidVisitorAdapter<Object> {
    	
    	private ClassSplitter splitter = null;
    	
    	public MethodVisitor(ClassSplitter splitter) {
    		this.splitter = splitter;
    	}
    	
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            
        	MethodInfo methodInfo = new MethodInfo(n);
            splitter.methods.add(methodInfo);
            
            methodInfo.extractAttributesInUse(this.splitter.getAttributes());
        }
    }

	public List<MethodInfo> getMethods() {
		return this.methods;
	}



    
	

}
