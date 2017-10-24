
public class tester
{
	public static void main(String[] args) throws InterruptedException
	{
		
		MyMonitor q = new MyMonitor(50);
		ThreadPool test = new ThreadPool(q);
		test.startPool();
		

		Thread manager = new Thread(new ManagerRun(q, test), "Manager thread");
		manager.start();
		Thread[] threads = new Thread[100];
		for(int i = 0; i < threads.length; i++)
		{
			threads[i] = new Thread(new Runnable(){

				@Override
				public void run()
				{
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					q.add("DIV,3,5");
				}
				
			});
		}
		for(int j = 0; j < threads.length; j++)
		{
			threads[j].start();
		}
		
		
		
		Thread.sleep(5000);
		
		for(int i = 0; i < threads.length; i++)
		{
			threads[i] = new Thread(new Runnable(){

				@Override
				public void run()
				{
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					q.add("MUL,3,5");
				}
				
			});
		}
		for(int j = 0; j < threads.length; j++)
		{
			threads[j].start();
		}
		
		
		Thread.sleep(5000);
		
		
		Thread kill = new Thread(new Runnable(){

			@Override
			public void run()
			{
				q.add("KILL");
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
		});
		kill.start();
		kill.join();
		
	}
}
