package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class IncludePathParser 
{

	public List<String> parse(String includePath) 
	{
		List<String> result = new ArrayList<String>();
		StringTokenizer tokenize = new StringTokenizer(includePath, ";"); 
		while (tokenize.hasMoreTokens()) 
		{
			result.add(tokenize.nextToken());			
		}
		
		return result;
	}

}
