package fitnesse;

import java.io.IOException;

import domain.ClassSplitter;
import fit.ColumnFixture;

public class SplitClassFixture extends ColumnFixture {
	
	public static ClassSplitter splitter;
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean ok() {
		splitter = new ClassSplitter();
		try {
			splitter.splitClass(name);
			
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}
