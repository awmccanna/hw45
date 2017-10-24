import java.io.PrintWriter;

public class WorkerRun implements Runnable
{
	Job job;
	String order;
	double first, second;
	MyMonitor jobs;
	ThreadPool pool;
	PrintWriter out;
	
	public WorkerRun(MyMonitor jobQueue, ThreadPool thePool)
	{
		this.jobs = jobQueue;
		this.pool = thePool;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		Thread currentThread = Thread.currentThread();
		int index = this.pool.getIndex(currentThread);	
		
		
		while(jobs.keepGoing() && currentThread == this.pool.getThreadAt(index))
		{
			
			
			double result;
			job = jobs.getJob();//Thread will wait here if the queue is empty, MyMonitor tells thread to wait
			
			if(job != null)
			{
				order = job.getOrder();
				out = job.getPrinter();
				if(!order.equalsIgnoreCase("KILL"))//Will execute if there isn't a kill command
				{
					this.first = job.getFirst();
					this.second = job.getSecond();
					
					try
					{
						Thread.sleep(50);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
					if(order.equalsIgnoreCase("ADD"))//Addition
					{
						result = this.first + this.second;
						out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " + " + this.second + " = " + result);
						System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " + " + this.second + " = " + result);
					}
					else if(order.equalsIgnoreCase("SUB"))//Subtraction
					{
						result = this.first - this.second;
						out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " - " + this.second + " = " + result);
						System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " - " + this.second + " = " + result);
					}
					else if(order.equalsIgnoreCase("MUL"))//Multiplication
					{
						result = this.first * this.second;
						out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " * " + this.second + " = " + result);
						System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " * " + this.second + " = " + result);
					}
					else if(order.equalsIgnoreCase("DIV"))//Division
					{
						if(this.second == 0)
						{
							out.println(Thread.currentThread().getName() + " cannot divide by zero. " + job.getFullOrder() + " was not processed.");
							System.out.println(Thread.currentThread().getName() + " cannot divide by zero. " + job.getFullOrder() + " was not processed.");
						}
						else
						{
							result = this.first / this.second;
							out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " / " + this.second + " = " + result);
							System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " / " + this.second + " = " + result);
						}
					}
					else//Invalid input
					{
						out.println(Thread.currentThread().getName() + " recieved invalid command. " + job.getFullOrder() + " was not processed.");
						System.out.println(Thread.currentThread().getName() + " recieved invalid command. " + job.getFullOrder() + " was not processed.");
					}
					
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
				}
				else//Kill command recieved, telling monitor and pool to stop all the threads
				{
					try
					{
						Thread.sleep(50);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					pool.stopPool();
					System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ".");
					jobs.turnOff();
				}
			}
			
			
			
			
		}//end while
		
		System.out.println(Thread.currentThread().getName() + " terminating");
		
	}

}
