
public class ManagerRun implements Runnable
{
	private MyMonitor jobs;
	private ThreadPool pool;
	private int waitTime, t1, t2, t3;
	
	
	public ManagerRun(MyMonitor q, ThreadPool p)
	{
		jobs = q;
		t1 = 10;
		t2 = 20;
		t3 = 40;
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
			
			if(jobs.numJobs() > t1 && pool.numberThreadsRunning() == 5)//More than 10 jobs, but only 5 threads
			{
				System.out.println(jobs.numJobs() + " jobs in queue, " + pool.numberThreadsRunning() + " threads running. Doubling thread count");
				pool.increaseThreadsInPool();
			}
			else if(jobs.numJobs() > t2 && pool.numberThreadsRunning() == 10)//More than 20 jobs, only 10 threads
			{

				System.out.println(jobs.numJobs() + " jobs in queue, " + pool.numberThreadsRunning() + " threads running. Doubling thread count");
				pool.increaseThreadsInPool();
			}
			/*
			else if(jobs.numJobs() > block3 && pool.numberThreadsRunning() == 20)//More than 40 jobs, only 20 threads
			{

				System.out.println(jobs.numJobs() + " jobs in queue, " + pool.numberThreadsRunning() + " threads running. Doubling thread count");
				pool.increaseThreadsInPool();
			}
			else if(jobs.numJobs() < block3 && pool.numberThreadsRunning() == 40)//Less than 40 jobs, and 40 threads
			{

				System.out.println(jobs.numJobs() + " jobs in queue, " + pool.numberThreadsRunning() + " threads running. Halving thread count");
				pool.decreaseThreadsInPool();

			}*/
			else if(jobs.numJobs() < t2 && pool.numberThreadsRunning() == 20)//Less than 20 jobs, and 20 threads
			{

				System.out.println(jobs.numJobs() + " jobs in queue, " + pool.numberThreadsRunning() + " threads running. Halving thread count");
				pool.decreaseThreadsInPool();

			}
			else if(jobs.numJobs() < t1 && pool.numberThreadsRunning() == 10)//Less than 10 jobs, 10 threads or more
			{

				System.out.println(jobs.numJobs() + " jobs in queue, " + pool.numberThreadsRunning() + " threads running. Halving thread count");
				pool.decreaseThreadsInPool();
				
			}

			
			
		}//end while
		System.out.println(Thread.currentThread().getName() + " terminating");
	}

}
