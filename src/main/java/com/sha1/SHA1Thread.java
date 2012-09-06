package com.sha1;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class SHA1Thread implements Callable<Report>
{

	private static HashMap<String,String> h = new HashMap<String,String>();
	
	
	private File path = null;


	public Report call() throws Exception
	{
		System.out.println("Scanning:" + path + "...");
		
		Report report = new Report(this.path);
		 /*
		   * An array of abstract pathnames denoting the files and directories in the directory denoted by this abstract pathname. 
		   * The array will be empty if the directory is empty. 
		   * Returns null if this abstract pathname does not denote a directory, or if an I/O error occurs.
		   *  
		   */
		File[] l = path.listFiles();
		for (File f : l)
		{
			if (f.isFile())
			{


				String m = Util.sha1(f);
				
				// Este bloque es *sincronizado* porque accede a la tabla global y comun (h) que recoge 
				// los pares ruta - SHA1 
				// Nota: El calculo SHA1 no debe ir en este bloque porque queremos usar 
				// la potencia multihilo para paralelizar los calculos intensivos, como es SHA1
				
				synchronized(SHA1Thread.class)
				{
					
					if (h.values().contains(m))
					{
						Set<Map.Entry<String, String>> s = h.entrySet();
						for (Map.Entry<String, String> e : s)
						{
							System.out.println(e.getValue() + "<--->" + m);
							if (e.getValue().equals(m))
								report.add(e.getKey(),f.getCanonicalPath());

						}
					}

					
					h.put(f.getCanonicalPath(), m);
					
				}
				
			}
			else if (f.isDirectory())
			{
				
				SHA1ThreadPool.add(new SHA1Thread(f));
			
			}

		}
	
		System.out.println("Finish scan:" + path);
		return report;
	}

	public SHA1Thread(File path)
	{
		super();
		
		this.path = path;
	
	}
	
	
	

}
