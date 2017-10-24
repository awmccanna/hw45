import java.io.PrintWriter;

public class Job
{
	private String order, clientThread, fullOrder;
	private int first, second;
	private PrintWriter out;
	
	public Job()
	{
		this.order = "";
		this.clientThread = "";
		this.first = 1;
		this.second = 1;
	}
	
	/**
	 * 
	 * @param tID- Name of the client thread which made the request
	 * @param o- Request string, should be formatted as "ADD,4,5" or something similar
	 */
	public Job(String tID, String o, PrintWriter printHere)
	{
		this.clientThread = tID;
		this.fullOrder = o;
		this.out = printHere;
		parseVars(o);
	}
	
	
	/**
	 * 
	 * @param o - The string that was sent by the client, will be parsed into its usable pieces.
	 */
	private void parseVars(String o)
	{
		String[] splitString = o.split(",");
		this.order = splitString[0];
		
		try{
			this.first = Integer.parseInt(splitString[1]);
		} catch(NumberFormatException e){
			//Wasn't a number to be parsed
			this.first = 0;
		} catch(ArrayIndexOutOfBoundsException e){
			//Doesn't exist
			this.first = 0;
		} catch(Exception e){
			//dunno lol
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		try{
			this.second = Integer.parseInt(splitString[2]);
		} catch(NumberFormatException e){
			//Wasn't a number to be parsed
			this.second = 0;
		} catch(ArrayIndexOutOfBoundsException e){
			//Doesn't exist
			this.second=0;
		} catch(Exception e){
			//dunno lol
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public String getOrder()
	{
		return this.order;
	}
	
	public int getFirst()
	{
		return this.first;
	}
	
	public int getSecond()
	{
		return this.second;
	}
	
	public String getClientName()
	{
		return this.clientThread;
	}
	
	public String getFullOrder()
	{
		return this.fullOrder;
	}
	
	public PrintWriter getPrinter()
	{
		return this.out;
	}
}
