package test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import domain.AttributeInfo;
import domain.ClassSplitter;
import domain.MethodInfo;

import tablelize.Fixture;
import tablelize.Table;

public class ClassSplitterFixture extends Fixture {
	
	private ClassSplitter splitter = null;
	
	@Override
	public void execute() throws Exception {
		
		split();
		
		assertMethodNames();
		assertAttributesName();
		
		assertMethods();
	}
	
	private void assertMethods() throws Exception {
		
		if (!this.hasTable("method")) {
			Assert.assertEquals(0, splitter.getMethodCount());
			return;
		}
		
		List<Table> tables = this.getTablesWithName("method");		
		List<MethodInfo> methods = splitter.getMethods();
		
		Assert.assertEquals(tables.size(), methods.size());
		
		for (MethodInfo method : methods) {
			
			Table table = this.getTableWithArg("method", 0, method.getName());
			
			if (!method.useAttributes()) {
				Assert.assertEquals(table.getCell("use", 0), "-");
			}
			else {
				List<AttributeInfo> usedAttributes = method.getUsedAttributes();
				Assert.assertEquals(table.rowsCount(), usedAttributes.size());
				
				for (int i = 0; i < usedAttributes.size(); ++i) {
					
					AttributeInfo usedAttr = usedAttributes.get(i);
					Assert.assertEquals(table.getCell("use", i), usedAttr.getName());
				}
			}
		}			
		
		
	}

	private void assertAttributesName() throws Exception {
		
		if (!this.hasTable("should have attributes")) {
			Assert.assertEquals(0, splitter.getAttributeCount());
			return;
		}
		
		Table table = this.getTable("should have attributes");
		Assert.assertEquals(table.rowsCount(), splitter.getAttributeCount());
		
		List<AttributeInfo> attributes = splitter.getAttributes();
		
		for (int i = 0; i < attributes.size(); ++i) {
			AttributeInfo attribute = attributes.get(i);
			Assert.assertEquals(table.getCell("name", i), attribute.getName());
		}
	}

	private void assertMethodNames() throws Exception {
		
		if (!this.hasTable("should have methods")) {
			Assert.assertEquals(0, splitter.getMethodCount());
			return;
		}
		
		Table table = this.getTable("should have methods");
		Assert.assertEquals(table.rowsCount(), splitter.getMethodCount());
		
		List<MethodInfo> methods = splitter.getMethods();
		
		for (int i = 0; i < methods.size(); ++i) {
			MethodInfo method = methods.get(i);
			Assert.assertEquals(table.getCell("name", i), method.getName());
		}
	}

	private void split() throws Exception {
		
		Table table = this.getTable("split");
		String className = table.getCell("class", 0);
		splitter = new ClassSplitter();
		splitter.splitClass("resource/" + className);
		
	}

	
	// TODO: colocar isso no tablelize-it
	public void LoadDataFromFolder(String folder) throws Exception {
		File[] files = new File(folder).listFiles(new FilenameFilter() { 
	         public boolean accept(File dir, String filename)
             { return filename.endsWith(".table"); }
		} );

		for (File file : files) {
		    if (file.isFile()) {
		    	this.loadDataFromFile(file.getAbsolutePath());
		    }
		}		
	}
	
	@Test
	public void loadAndTestTables() throws Exception {
		
		ClassSplitterFixture fixture = new ClassSplitterFixture();
		fixture.LoadDataFromFolder("resource");
	}
}

