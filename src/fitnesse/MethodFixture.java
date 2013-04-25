package fitnesse;

import java.util.List;

import domain.AttributeInfo;
import domain.ClassSplitter;
import domain.MethodInfo;
import fit.RowFixture;

public class MethodFixture extends RowFixture {
	
	class MethodUsedAttribute {
		private String use;
		
		public MethodUsedAttribute(String attributeName) {
			this.use = attributeName;
		}
		
		public String getUse() {
			return this.use;
		}
	}

	@Override
	public Class<?> getTargetClass() {
		return MethodUsedAttribute.class;
	}

	@Override
	public Object[] query() throws Exception {
		
		ClassSplitter splitter = SplitClassFixture.splitter;
		MethodInfo method = splitter.getMethodInfoFor(this.args[0]);
		
		List<AttributeInfo> attributes = method.getUsedAttributes();
		
		MethodUsedAttribute[] result = new MethodUsedAttribute[attributes.size()];
		
		for (int i = 0; i < attributes.size(); ++i) {
			result[i] = new MethodUsedAttribute(attributes.get(0).getName());
		}
		return result;
	}
	
	

}
