package com.sha1;

import java.io.File;
import java.util.concurrent.Future;


public class SHA1Main
{
	
	/*
	However, MD5 is not secure anymore because researchers have proven that it is possible to manipulate the original data to get the same MD5 as the original. 
	The alternative is to use SHA-1 which is on 160-bits (MD5 is 128-bits). It's still not perfect but it is better than MD5.
	*/
	
	
	private static String path = "C:\\x";		// Default 
	
	
	
	public static void main(String[] a)
	{
		
			if (a.length > 0)
			{
				if (a.length > 3)
				{
					usage("Wrong parameter number");
					return;
				}
				for (int i=0; i<a.length; i++)
				{
					if (!a[i].startsWith("-") )
					{
						if (i != a.length - 1)
						{
							usage("Wrong parameter:" + a[i]);
							return;
						}
						else
						{
							File f = new File(a[i]);
							if (!f.exists())
							{
								usage(a[i] + " not found");
								return;
							}
							if (!f.isDirectory())
							{	
								usage(a[i] + " is not a directory");
								return;
							}
							path = a[i];
						}
					}
				
					if (a[i].startsWith("-") && !a[i].matches("-h"))
					{
						usage("Unknown parameter" + a[i]);
						return;
					}
					
					if ("-h".equals(a[i]))
					{
						usage(null);
						return;
					}
										
					// TODO		Check path is right 
						
				}

			}

			try
			{
				SHA1ThreadPool.add(new SHA1Thread(new File(path)));
				
			
				// Active checking about finishing 
				SHA1ThreadPool.await();
				
				System.out.println("===Report====");
	            for (Future<Report> future : SHA1ThreadPool.getFuture())
	            {

	                Report report = future.get();
	                if (report != null)
	                {
	                    report.print();
	                }
	            }
	            System.out.println("=============");

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
			
			System.out.println("Finished");
			System.exit(0);
	}
	
	
	
	private static void usage(String m)
	{
		if ( m != null)
			System.out.println(m);
		System.out.println("Usage: eqf [-h][path]");
		System.out.println("-h: show this help");
		// System.out.println("-r: recursive (slow)");
		
	}
	

}
