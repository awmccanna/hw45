
public class ThreadPool
{
	int maxCapacity;
	int actualNumberThreads;
	WorkerThread holder[];
	boolean stopped;
	static MyMonitor jobQueue;
	Thread monitor;
	
	private class WorkerThread extends Thread
	{
		public WorkerThread(Runnable r, String string)
		{
			super(r, string);
		}
	}
	
	
	public ThreadPool()
	{
		this.maxCapacity = 40;
		this.actualNumberThreads = 5;
		this.holder = new WorkerThread[40];
		this.stopped = false;
		jobQueue = new MyMonitor(50);
		monitor = new Thread(new MonitorRun(jobQueue, this), "Monitor thread");
		
		
		for(int i = 0; i < 5; i++)
		{
			holder[i] = new WorkerThread(new WorkerRun(jobQueue, this), "Thread " + i);
		}
		
	}
	
	public ThreadPool(MyMonitor p)
	{
		this.maxCapacity = 40;
		this.actualNumberThreads = 5;
		this.holder = new WorkerThread[40];
		this.stopped = false;
		jobQueue = p;
		monitor = new Thread(new MonitorRun(jobQueue, this), "Monitor thread");
		
		
		for(int i = 0; i < 5; i++)
		{
			holder[i] = new WorkerThread(new WorkerRun(jobQueue, this), "Thread " + i);
		}
	}
	
	
	
	
	
	public void startPool()
	{
		for(int i = 0; i < 5; i++)
		{
			holder[i].start();
		}
		monitor.start();
	}
	
	public void increaseThreadsInPool()
	{
		if(this.actualNumberThreads != 20)
		{
			for(int i = this.actualNumberThreads; i < this.actualNumberThreads*2; i++)
			{
				holder[i] = new WorkerThread(new WorkerRun(jobQueue, this), "Thread " + i);
				holder[i].start();
			}
			
			this.actualNumberThreads *= 2;
		}
	}
	
	public void decreaseThreadsInPool()
	{
		if(this.actualNumberThreads != 5)
		{
			for(int i = this.actualNumberThreads; i > this.actualNumberThreads/2; i--)
			{
				holder[i] = null;//Will cause one of the conditions on the threads while loop to fail, so the thread will exit
			}
			this.actualNumberThreads /=2;
		}
	}
	
	public void stopPool()
	{
		for(int i = 0; i < holder.length; i++)
		{
			holder[i] = null;
		}
	}
	
	public int numberThreadsRunning()
	{
		return this.actualNumberThreads;
	}
	
	public int maxCapacity()
	{
		return this.maxCapacity;
	}
	
	
	public int getIndex( Thread current )
	{
		for ( int i = 0; i < this.actualNumberThreads; i++ )
			if ( current == holder[ i ] )
				return i;

		return -1; 
	}



	public Thread getThreadAt(int index)
	{
		return holder[index];
	}
	
	
	
	
}
