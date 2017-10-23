
public class MonitorRun implements Runnable
{
	private MyMonitor jobs;
	private ThreadPool pool;
	private int waitTime, block1, block2;
	
	
	public MonitorRun(MyMonitor q, ThreadPool p)
	{
		jobs = q;
		block1 = 10;
		block2 = 20;
		waitTime = 500;
		pool = p;
	}
	
	
	
	@Override
	public void run()
	{
		while(jobs.keepGoing())
		{
			try
			{
				Thread.sleep(waitTime);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			if(jobs.numJobs() > block1 && pool.numberThreadsRunning() == 5)//More than ten jobs, but only 5 threads
			{
				//TODO increment pool to 10 threads
			}
			else if(jobs.numJobs() > block2 && pool.numberThreadsRunning() <= 10)//More than 20 jobs, only 10 threads or less
			{
				//TODO increment pool to 20 threads
			}
			else if(jobs.numJobs() < block2 && pool.numberThreadsRunning() == 20)//Less than 20 jobs, and 20 threads
			{
				//TODO decrement pool
			}
			else if(jobs.numJobs() < block1 && pool.numberThreadsRunning() >= 10)//Less than 10 jobs, 10 threads or more
			{
				//TODO
			}
			
			
			
			
			
			
			
			
		}//end while
		System.out.println(Thread.currentThread().getName() + " terminating");
	}

}
