package com.sha1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * Informe para un directorio
 * 
 */

public class Report 
{

	@SuppressWarnings("unused")
	private File path = null;
	
	
	private List<String> l = null;
	
	
	public Report(File path)
	{
		super();
		
		this.path = path;

	}
	
	public void add(String f1, String f2)
	{
		if (l == null)
			l  = new ArrayList<String>();
		
		l.add(f1 + "=" + f2);
	}
	
	
	public void print() throws Exception
	{
		if ( l != null && l.size() > 0)
		{
			
			for (String s:l)
			{
				System.out.println("\t" + s);
			}
		}
		
		
	}

}
