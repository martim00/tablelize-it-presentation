package fitnesse;


import java.util.List;

import domain.AttributeInfo;
import domain.ClassSplitter;
import fit.RowFixture;

public class ShouldHaveAttributesFixture extends RowFixture {

	@Override
	public Class<?> getTargetClass() {
		return Attribute.class;
	}
	
	class Attribute {
		private String name;
		public Attribute(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}

	@Override
	public Object[] query() throws Exception {
		ClassSplitter splitter = SplitClassFixture.splitter;
		List<AttributeInfo> attributes = splitter.getAttributes();
		
		Attribute[] result = new Attribute[attributes.size()];
		for (int i = 0; i < attributes.size(); ++i) {
			result[i] = new Attribute(attributes.get(i).getName());
		}
		return result;
	}
}
