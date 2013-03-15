package selfstudy.concurrency.testobjects;

/**
 * A placeholder object which accomplishes exclusive access via the {@link #wait} and {@link #notifyAll} idiom.<br/>
 * The relevant methods are marked as <code>synchronized</code> in order to obtain the inherent object monitor/lock.
 * 
 * @author grandre
 */
public class WaitNotifyObject implements RunnableSharedObject {

	boolean isFree = false;
	
	public synchronized void use(String threadname) {
		
		System.out.println( threadname + " waiting to acquire lock..." );
		
		while(!isFree) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println( threadname + " got lock, working..." );
		
		relinquish();
	}
	
	public synchronized void relinquish() {

		isFree = true;
		notifyAll();
	}
	
}
