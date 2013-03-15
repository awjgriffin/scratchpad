package selfstudy.concurrency.testobjects;
/**
 * A {@link Runnable} implementation that wraps an abstract {@link #process} method with a
 * non-cancelling interruption policy.<br/> 
 * Before exiting, the object resets its <code>interrupted</code> status to true.  
 * 
 * @author grandre
 */
public abstract class NonCancellingRunnable implements Runnable {

	public void run() {

		boolean interrupted = false;
		
		try {
			
			while(true) {
				
				try {
						
					process();
					
				} catch (InterruptedException e) {
					
					System.out.println("Received interrupt, but ignoring...");
					interrupted = true;
					// do nothing, just retry.
					// this does not break out of the while loop, and therefore does not execute
					// the surrounding finally statement.
				}
				
			}
			
		} finally {
			
			if (interrupted) {
				Thread.currentThread().interrupt();   // resets interrupt before leaving
			}
		}
		
	}
	
	/**
	 * This throws {@link InterruptedException} in order to fit in with the waiting idiom.<br/>
	 * To simulate a blocking call, <code>Object.wait()</code> or an endless loop can be used if no actual blocking method exists.
	 * @throws InterruptedException
	 */
	protected abstract void process() throws InterruptedException;
	
}
