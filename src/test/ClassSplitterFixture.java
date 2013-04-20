package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.Test;

import tablelize.Fixture;

public class ClassSplitterFixture extends Fixture {

	@Override
	public void execute() throws Exception {
		System.out.println("executing...");
	}
	
	public void LoadDataFromFolder(String folder) throws Exception {
		File[] files = new File(folder).listFiles(new FilenameFilter() { 
	         public boolean accept(File dir, String filename)
             { return filename.endsWith(".table"); }
		} );

		for (File file : files) {
		    if (file.isFile()) {
		        this.LoadingDataFromFile(file.getAbsolutePath());
		    }
		}		
	}
	
	@Test
	public void loadAndTestTables() throws Exception {
		
		ClassSplitterFixture fixture = new ClassSplitterFixture();
		fixture.LoadDataFromFolder("resource");
	}
}

