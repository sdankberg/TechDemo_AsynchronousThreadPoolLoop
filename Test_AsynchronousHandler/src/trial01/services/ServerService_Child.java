package trial01.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ServerService_Child extends ServerService_Parent {
	
	
	/**
	 * Constructor - records "service" ID in parent
	 * @param i
	 */
	public ServerService_Child(final int i) {
		super(i);
	}

	
	
	/**
	 * onFuture - AFTER onCall() - Output of Service Completion
	 */
	@Override
	public <T> T onFuture(Future<T> future) throws ExecutionException, InterruptedException {
		System.out.println("Service Handled-id: " + this.id + " - " + future.get() + " millis");
		return null;
	}


	
	/**
	 * onCall() - When "TaskSubmitted" - what the "Service" does
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Callable<?> onCall() {
		return (Callable<?>) new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long millis = (long) (Math.pow(2000, Math.random()));
                Thread.sleep(millis);
                return millis;
            }
        };
	}
	
	
} //end class ServerService_Child
 