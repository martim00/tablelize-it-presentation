
public class ClassWithTwoMethodsAndTwoAttributes
{
	private int attr1 = 0;
	private String attr2 = 0;
	
	public void foo() {
		attr1 = 1;
		attr2 = "";
	}
	
	private boolean roo() {
		return attr1 != 0;
	}
}