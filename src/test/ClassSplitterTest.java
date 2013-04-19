package test;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import domain.AttributeInfo;
import domain.ClassSplitter;
import domain.MethodInfo;

public class ClassSplitterTest {
	
	
	ClassSplitter splitClass(String path) throws IOException 
	{
		ClassSplitter splitter = new ClassSplitter();
		splitter.splitClass(path);
		return splitter;
	}
	
	@Test
	public void testSplitClassWithOneMethod() throws IOException
	{
		ClassSplitter splitter = splitClass("resource/ClassWithOneMethod.java");
		Assert.assertEquals(1, splitter.getMethodCount());
		Assert.assertEquals(0, splitter.getAttributeCount());
	}
	
	@Test
	public void testSplitClassWithOneAttribute() throws IOException
	{
		ClassSplitter splitter = splitClass("resource/ClassWithOneAttribute.java");
		Assert.assertEquals(0, splitter.getMethodCount());
		Assert.assertEquals(1, splitter.getAttributeCount());
	}
	
	@Test
	public void testSplitClassWithOneMethodUsingOneAttribute() throws IOException
	{
		ClassSplitter splitter = splitClass("resource/ClassWithMethodUsingOneAttribute.java");
		Assert.assertEquals(1, splitter.getMethodCount());
		Assert.assertEquals(1, splitter.getAttributeCount());
		
		MethodInfo methodInfo = splitter.getMethodInfoFor("method");
		Assert.assertNotNull(methodInfo);
		List<AttributeInfo> attributes = methodInfo.getUsedAttributes();
		Assert.assertEquals(1, attributes.size());
		Assert.assertEquals("attr", attributes.get(0).toString());
	}
	
	
	
	

}
