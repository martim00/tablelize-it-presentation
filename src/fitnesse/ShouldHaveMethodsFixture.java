package fitnesse;

import java.util.List;

import domain.ClassSplitter;
import domain.MethodInfo;
import fit.RowFixture;

public class ShouldHaveMethodsFixture extends RowFixture {
	
	@Override
	public Class<?> getTargetClass() {
		return Method.class;
	}
	
	class Method {
		private String name;
		public Method(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}

	@Override
	public Object[] query() throws Exception {
		ClassSplitter splitter = SplitClassFixture.splitter;
		List<MethodInfo> methods = splitter.getMethods();
		
		Method [] result = new Method[methods.size()];
		for (int i = 0; i < methods.size(); ++i) {
			result[i] = new Method(methods.get(i).getName());
		}
		
		return result;
	}
	

}
