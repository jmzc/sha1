package com.sha1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class Util 
{

	/*
	 * Message digests are secure one-way hash functions that take arbitrary-sized data and output a fixed-length hash value.
	 */
	public static String sha1(File f) throws Exception
	{
		 InputStream fis =  new FileInputStream(f);

	     byte[] buffer = new byte[1024];
	     MessageDigest sha1 = MessageDigest.getInstance("SHA1");
	     int n;
	     do 
	     {
	      n = fis.read(buffer);
	      if (n > 0) 
	      {
	        sha1.update(buffer, 0, n);
	      }
	     } 
	     while (n != -1);
	     
	     fis.close();
	     
	     byte[] b = sha1.digest();
	     
	     StringBuffer r = new StringBuffer();
	     for (int i=0; i < b.length; i++) 
	     {
	          r.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring(1));
	     }

	    return r.toString();
	  
	
	}
	
}
