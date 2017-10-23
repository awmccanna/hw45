
public class WorkerRun implements Runnable
{
	Job job;
	String order;
	int first, second;
	MyMonitor jobs;
	ThreadPool pool;
	
	public WorkerRun(MyMonitor jobQueue, ThreadPool thePool)
	{
		this.jobs = jobQueue;
		this.pool = thePool;
	}

	@Override
	public void run()
	{
		Thread currentThread = Thread.currentThread();
		int index = this.pool.getIndex(currentThread);
		
		
		
		while(jobs.keepGoing() && currentThread == this.pool.getThreadAt(index))
		{

			System.out.println("Worker " + currentThread.getName() + " with index of " + index + " is running, inside of while loop");
			int result;
			job = jobs.getJob();//Thread will wait here if the queue is empty, MyMonitor tells thread to wait
			order = job.getOrder();

			System.out.println("Worker " + currentThread.getName() + " with index of " + index + " is running, inside of while loop. My order is: " + order);
			if(!order.equalsIgnoreCase("KILL"))//Will execute if there isn't a kill command
			{
				this.first = job.getFirst();
				this.second = job.getSecond();
				
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
				if(order.equalsIgnoreCase("ADD"))//Addition
				{
					result = this.first + this.second;
					System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " + " + this.second + " = " + result);
				}
				else if(order.equalsIgnoreCase("SUB"))//Subtraction
				{
					result = this.first - this.second;
					System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " - " + this.second + " = " + result);
				}
				else if(order.equalsIgnoreCase("MUL"))//Multiplication
				{
					result = this.first * this.second;
					System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " * " + this.second + " = " + result);
				}
				else if(order.equalsIgnoreCase("DIV"))//Division
				{
					if(this.second == 0)
					{
						System.out.println(Thread.currentThread().getName() + " cannot divide by zero. " + job.getFullOrder() + " was not processed.");
					}
					else
					{
						result = this.first / this.second;
						System.out.println(Thread.currentThread().getName() + " has processed " + job.getFullOrder() + ". The result is " + this.first + " / " + this.second + " = " + result);
					}
				}
				else//Invalid input
				{
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
			else//Kill command recieved, telling monitor to stop all the threads
			{			
				pool.stopPool();
				jobs.turnOff();
			}
			
			
			
		}//end while
		
		System.out.println(Thread.currentThread().getName() + " terminating");
		
	}

}
