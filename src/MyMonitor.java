import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;


public class MyMonitor
{
	private Queue<Job> queue;
	private int max;
	private boolean cont;
	
	public MyMonitor(int size)
	{
		queue = new LinkedList<Job>();
		max = size;
		cont = true;
	}
	
	public synchronized void add(String in, PrintWriter printer)
	{
		String name = Thread.currentThread().getName();
		Job toAdd = new Job(name, in, printer);
		while(isFull())
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		queue.add(toAdd);
		notifyAll();
	}
	
	
	public synchronized Job getJob()
	{
		
		while(isEmpty() && this.cont)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		Job toReturn = queue.poll();
		notifyAll();
		return toReturn;
	}
	
	private boolean isEmpty()
	{
		return (queue.peek() == null);
	}
	
	private boolean isFull()
	{
		if(queue.size() >= max)
		{
			return true;
		}
		return false;
	}
	
	public synchronized boolean keepGoing()
	{
		return cont;
	}
	
	public synchronized void turnOff()
	{
		cont = false;
		notifyAll();
	}
	
	public synchronized int numJobs()
	{
		return this.queue.size();
	}

}
