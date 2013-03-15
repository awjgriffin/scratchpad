package selfstudy.concurrency;

import org.junit.Test;

import selfstudy.concurrency.testobjects.EndlessDoNothingTask;

public class InterruptionTests {
	
	/**
	 * Starts a {@link EndlessDoNothingTask} and then loops ten times, interrupting the thread each time.<br/>
	 * This forces the thread's <code>process()</code> to be called ten times.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testNonCancellingInterrupt() throws InterruptedException {
		
		Thread thread = new Thread(new EndlessDoNothingTask());
		thread.start();
		
		for(int i = 1; i <= 10; i++){
			
			Thread.sleep(100 * i);
			thread.interrupt();
		}

		thread.join(2000);   // this is necessary, otherwise the currentThread will exit without waiting.  
	}

	/**
	 * Starts a {@link EndlessDoNothingTask} and then loops ten times, interrupting the thread each time.<br/>
	 * This method <i>doesn't</i> loop, so the <code>process()</code> method is called within the loop but
	 * waits on the second iteration because the single Semaphore permit was taken the first time around.<br/>
	 * Does this demonstrate the non-re-entrant locking of Semaphore?
	 * 
	 * @throws InterruptedException
	 */	
	@Test
	public void testNonCancellingInterrupt2() throws InterruptedException {
		
		Thread thread = new Thread(new EndlessDoNothingTask());
		thread.start();

		thread.join(2000);   // this is necessary, otherwise the currentThread will exit without waiting.  
	}	
	
	
}
