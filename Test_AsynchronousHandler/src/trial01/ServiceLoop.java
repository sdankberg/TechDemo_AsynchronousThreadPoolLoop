package trial01;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import trial01.interfaces.IServerService;

public class ServiceLoop {

	
	//Constants - How many threads in Callable/Future threadpools
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    private final ExecutorService result = Executors.newFixedThreadPool(10);
    
    //LIST
    private static Queue<IServerService> Services = new LinkedList<>();
    
    
    
    /**
     * AddService - threadsafe - adds "service" to list
     * @param service
     */
    public static void AddService(IServerService service) {
    	synchronized(Services) {
    		Services.add(service);
    	}
    }
    
    
    
    /**
     * IServerService - threadsafe - Pops from QueueList
     * @return
     */
    public static IServerService executeService() {
    	synchronized(Services) {
    		return Services.poll();
    	}
    }
	
    
    
    /**
     * AnsychonrousLoop - Event Handler Loop - Exits when no service to run for 2sec
     * @throws InterruptedException
     */
	public static void asynchronousLoop() throws InterruptedException {
		ServiceLoop main = new ServiceLoop();
		
		int exitCount = 0;
        while(true) {
        	
        	if(!Services.isEmpty()) {
        		main.submitTask(executeService());
        		exitCount = 0;
        	}
        	else {
        		exitCount++;
        		if(exitCount > 2000) {
        			System.out.println("\tExit Loop - No task for 2seconds");
        			break;
        		}
        	}
        		
        	Thread.sleep(5);
        }
        main.shutdown();
	}



	/**
	 * submitTask - threadsafe - handles task from loop via threadpool
	 * @param service
	 */
    public synchronized <T> void submitTask(IServerService service) {
		final Future<T> future = pool.submit(service.onCall());
        result.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    service.onFuture(future);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    
    /**
     * Closes both threadpools
     * @throws InterruptedException
     */
    public void shutdown() throws InterruptedException {
        pool.shutdown();
        result.shutdown();
    }
}
