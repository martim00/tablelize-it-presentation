package test;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import domain.IncludePathParser;

public class IncludePathParserTest {

	@Test
	public void testOnePath() {
		
		final String includePath = "domain";	
		
		IncludePathParser parser = new IncludePathParser();
		List<String> paths = parser.parse(includePath);
		
		Assert.assertEquals(1, paths.size());
	}
	
	@Test
	public void testTwoPaths() {
		final String includePath = "domain;test";
		IncludePathParser parser = new IncludePathParser();
		List<String> paths = parser.parse(includePath);
		
		Assert.assertEquals(2, paths.size());
	}

}
