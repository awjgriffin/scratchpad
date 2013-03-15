package selfstudy.concurrency.testobjects;

import java.util.concurrent.Semaphore;

public class SemaphoreLockedObject implements RunnableSharedObject {

	final private Semaphore semaphore = new Semaphore(1);
	
	public void use(String threadname) {
		
		try {
			
			System.out.println( threadname + " waiting to acquire lock..." );
			
			semaphore.acquire();  // this blocks; no need for a while loop
			
			System.out.println( threadname + " got lock, working..." );
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		relinquish();
	}

	public void relinquish() {
		semaphore.release();
	}

}
