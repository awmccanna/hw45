
public class tester
{
	public static void main(String[] args) throws InterruptedException
	{
		
		MyMonitor q = new MyMonitor(10);
		ThreadPool test = new ThreadPool(q);
		test.startPool();
		Thread[] threads = new Thread[10];
		for(int i = 0; i < threads.length; i++)
		{
			threads[i] = new Thread(new Runnable(){

				@Override
				public void run()
				{
					q.add("ADD,1,1");
					System.out.println("Adding order");
				}
				
			});
		}
		for(int j = 0; j < threads.length; j++)
		{
			threads[j].start();
		}
		
		Thread kill = new Thread(new Runnable(){

			@Override
			public void run()
			{
				q.add("KILL");
				System.out.println("Added kill");
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		kill.start();
	}
}
