package selfstudy.concurrency.testobjects;

import java.util.concurrent.Semaphore;

	public class EndlessDoNothingTask extends NonCancellingRunnable {

		// Sempahore is non-reentrant, so even if Thread A has the lock, it can't come back in and re-acquire it a
		// second time...
		final private Semaphore MUTEX = new Semaphore(1);
		
		@Override
		protected void process() throws InterruptedException {
	
			System.out.println("Acquiring mutex...");
			MUTEX.acquire();		// a simple blocking call (after it receives the first semaphore permit)
		}
}
