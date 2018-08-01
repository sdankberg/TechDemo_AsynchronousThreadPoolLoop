package trial01;

import trial01.services.ServerService_Child;

/**
 * 
 * @author Sam
 *
 * 1) Populates a LinkedList (Used as a Queue) with dummy rnd sleep services
 * 2) Begins to Populate LinkedList periodically to add services over time
 * 3) Begin Asynchronous Event Handler Loop to handle services
 * 
 * Future Ideas to Tweak
 * 		- ServiceLoop be a Singleton initialized in Main with params (num threads for pool)
 *		- use JUnit for the pre and run list population (since its for testing anyways)
 *		- Learn proper terminology for this structure??
 */
public class ServerMain {
	
	
	//Constants for Testing - In List BEFORE Loop
	public static final int pre_start = 0;
	public static final int pre_end	  = 500;
	
	//Constants for Testing - Add List WHILE Loop
	public static final int run_start = 501;
	public static final int run_end   = 521;
	
	
	/**
	 * MAIN - Init List - Runs Loop - Adds to List while running
	 * @param args
	 * @throws InterruptedException
	 */
    public static void main(String[] args) throws InterruptedException {
    	
    	populateList();
    	populateListPeriodically();
    	runLoop();
    }
    
    
    
    /**
     * PRE Loop Add Elements to List
     * @throws CloneNotSupportedException
     */
    public static void populateList() {
    	for(int i = pre_start; i < pre_end; i++) {
    		ServiceLoop.AddService(new ServerService_Child(i));
    	}
    }
    
    
    
    /**
     * Start Asynchronous Event Handling Loop
     */
    public static void runLoop() {
    	Thread t = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			try {
					ServiceLoop.asynchronousLoop();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	});
    	t.start();
    }
    
    
    
    /**
     * During Loop Add Elements to  List
     */
    public static void populateListPeriodically() {
    	//Populate List periodically
    	Thread t = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			
    			for(int i = run_start; i < run_end; i++) {
    				
					try {
    					ServiceLoop.AddService(new ServerService_Child(i));
    					System.out.println("Added Service: " + i);
						Thread.sleep(1000);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    			}
    		}
    	});
    	t.start();
    }
    
    
} //end class ServerMain