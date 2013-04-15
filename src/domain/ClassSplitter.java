package domain;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
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
		
	}
	
	public int getMethodCount() {
		return methods.size();
	}
	
	public int getAttributeCount() {
		return this.attributes.size();
	}
	
	private static class AttributeVisitor extends VoidVisitorAdapter<Object> {
		
		private ClassSplitter splitter = null;
    	
    	public AttributeVisitor(ClassSplitter splitter) {
    		this.splitter = splitter;
    	}
    	
		@Override
        public void visit(FieldDeclaration n, Object arg) {
			this.splitter.attributes.add(new AttributeInfo());
        }	
	}
	
    private static class MethodVisitor extends VoidVisitorAdapter<Object> {
    	
    	private ClassSplitter splitter = null;
    	
    	public MethodVisitor(ClassSplitter splitter) {
    		this.splitter = splitter;
    	}
    	
        @Override
    	public void visit(AssignExpr n, Object arg) {
        	System.out.println(n.toString());
        }	
        
        
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this 
            // CompilationUnit, including inner class methods
            
            splitter.methods.add(new MethodInfo(n.getName()));
            
            List<Statement> statements = n.getBody().getStmts();
            
            if (statements == null)
            	return;
            
            for (Statement statement : statements) {
            	//System.out.println(statement.toString());
            }
            
            super.visit(n, arg);
        }
    }

	public MethodInfo getMethodInfoFor(String methodName) {
		for (MethodInfo methodInfo : methods) {
			if (methodInfo.getName().equals(methodName))
				return methodInfo;
		}
		return null;
	}

    
	

}
