package com.sha1;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class SHA1ThreadPool
{

	private static ThreadPoolExecutor executor;
	
	private static List<Future<Report>> future = new ArrayList<Future<Report>>(9000);
	
	
	/*
     * 1) ThreadPoolExecutor es la implementación de referencia de la interfaz ExecutorService
     * 2) Se añaden tareas a la cola
     * 3) Se crean thread. Tiene un valor inicial y un valor maximo
     * 4) Si todos thread estan activos y no se ha alcanzado el maximo, se crea un nuevo thread y se le asigna una tarea de la cola
     * 5) Si todos thread estan activos y se ha alcanzado el maximo y hay tareas pendientes por asignar, se lanza el manejador
     * 6) El manejador me permite, por ejemplo, asignar la tarea a thread llamante
     */
	
	public static void add(SHA1Thread thread)
	{
		System.out.println("Adding new thread ...");
		if ( executor == null )
		{	
			executor = new ThreadPoolExecutor(30, 30, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		}
		
		future.add(executor.submit(thread));
			
	}
	
	public static void await() throws Exception
	{

		// awaitTermination() Blocks until all tasks have completed execution *after a shutdown request*, or the timeout occurs, or the current thread is interrupted, whichever happens first.
		// isTerminated() 	 Returns true if all tasks have completed *following shut down*. 
		while (executor.getActiveCount() > 0)
		{
			System.out.println("Waiting ... Completed:" + executor.getCompletedTaskCount() + ";Actived:" + executor.getActiveCount());
		}

	}
	
	public static List<Future<Report>> getFuture()
    {
        return future;
    }
	

	
		
}


