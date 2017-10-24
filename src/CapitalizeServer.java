import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server program which accepts requests from clients to
 * capitalize strings.  When clients connect, a new thread is
 * started to handle an interactive dialog in which the client
 * sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 */

/**
 * This server no longer works as decribed above. I have modified it to perform basic mathematical operations using threads
 * @author Alex McCanna
 *
 */
public class CapitalizeServer {

    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The basic math server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        /*********Code added by Alex McCanna*********
         * MyMonitor is an object which will hold new jobs coming in
         * ThreadPool is where all the worker threads will be held
         * manager is the Manager thread to monitor the ThreadPool and MyMonitor objects,
         * ensuring the correct number of threads during operation
         */
        MyMonitor queue = new MyMonitor(50);
		ThreadPool pool = new ThreadPool(queue);
		pool.startPool();
		Thread manager = new Thread(new ManagerRun(queue, pool), "Manager thread");
		manager.start();
		/********************************************/
        try { 
            while (true) {
                new MakeJob(listener.accept(), clientNumber++, queue).start();
            }
        } finally {
            listener.close();
        }
    }
    
    
    
    /**
     * 
     * @author Alex McCanna
     * Private Thread Class to make a new Job object with input from clients.
     * Borrowing heavily from Tonys example below
     *
     */
	private static class MakeJob extends Thread
	{
		private Socket socket;
		private int clientNumber;
		private MyMonitor queue;
		
		public MakeJob(Socket socket, int clientNumber, MyMonitor q) {
	        this.socket = socket;
	        this.clientNumber = clientNumber;
	        this.queue = q;
	        log("New connection with client# " + clientNumber + " at " + socket);
	    }
		
		//run() method is a modified version of Tonys run() method for the capitalization threads
		public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter \"ADD,X,Y\",\"SUB,X,Y\",\"MUL,X,Y\",\"DIV,X,Y\", KILL, or \".\".");
                out.println("KILL will terminate the server and all connections, \".\" will close this client only");

                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")){
                        break;
                    }
                    this.queue.add(input, out);
                    if(input.equalsIgnoreCase("KILL"))
                    {
                    	break;
                    }
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }
		
		private void log(String message) {
            System.out.println(message);
        }
	}
    
    
    
    
    
    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}