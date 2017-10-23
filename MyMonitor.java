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
	
	public synchronized void add(String in)
	{
		String name = Thread.currentThread().getName();
		Job toAdd = new Job(name, in);
		while(isFull())
		{
			try
			{
				wait();
				System.out.println("Queue is full, " + Thread.currentThread().getName() + " must wait.");
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		queue.add(toAdd);
		System.out.println("Job added, sending notify");
		notifyAll();
	}
	
	
	public synchronized Job getJob()
	{
		while(isEmpty())
		{
			try
			{

				System.out.println("Jobs is empty, " + Thread.currentThread().getName() + " must wait");
				System.out.println(queue.peek());
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		Job toReturn = queue.remove();
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
	}
	
	public synchronized int numJobs()
	{
		return this.queue.size();
	}
}
