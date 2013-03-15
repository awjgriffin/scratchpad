package selfstudy.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Ignore;
import org.junit.Test;

import selfstudy.concurrency.testobjects.ExplicitLockObject;
import selfstudy.concurrency.testobjects.RunnableSharedObject;
import selfstudy.concurrency.testobjects.WaitNotifyObject;

/**
 * 
 * Test guarded blocks using the {@link Thread#wait()} and {@link Thread#notifyAll()}
 * You can not specify a target for {@link Thread#notify()}; therefore it's only used in 
 * massively-parallel apps where it doesn't matter which thread is notified.
 * 
 * In order for the Guarded Block idiom to work,  (using wait/notify/notifyAll) 
 * every thread involved must be waiting for the same object monitor/lock.
 * 
 *
 * Using mutex's with synchronized blocks, as opposed to synchronizing entire methods, 
 * allows other threads to use other methods on the same host object.
 * 
 * Update: Actually, that's wrong, as per {@link Object}'s javadoc:
 *      synchronized (obj) {
         while (<condition does not hold>)
             obj.wait();
         ... // Perform action appropriate to condition
     }
 * I just wasn't using the object's wait method, see v1 below...
 *
 * @author grandre
 */
public class ExclusiveAccessTests {

	/**
	 * Version 4 uses a binary (single-permit) Semaphore.
	 */
	@Test
	public void testGuardedBlock_v4() {
		
		createThreads(new WaitNotifyObject());
	}
	
	
	/**
	 * Version 3 uses the {@link java.utils.concurrent.lock} libraries,
	 * which can be used in place of the {@link Object#wait} and {@link Object#notifyAll}
	 * Most of the inner workings of this method is in the {@link ExplicitLockObject} class itself.
	 */
	@Test 
	public void testGuardedBlock_v3() {
		
		createThreads(new ExplicitLockObject());
	}
	

	@Test
	public void testGuardedBlock_v2() {
		
		createThreads(new WaitNotifyObject());
	}
	
	
	
	private void createThreads(final RunnableSharedObject targetObject) {
		
		for(int i = 1; i <= 10; i++) {
		
			final String threadName = "Test Thread " + i;
			
			new Thread(new Runnable(){

				public void run() {
					targetObject.use(threadName);
				}
				
			}).start();
			
		}
		
		targetObject.relinquish();  // start off the whole process, otherwise all the threads hang in suspension.
		
		try {
			
			// Don't use join because we're waiting for multiple threads, 
			// so we just pause to let the threads (hopefully) complete their work
			Thread.sleep(3000);  
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
		
	/**
	 * Guarded Block v1 <br/> 
	 * This is all wrong, it would never work, and is my first, confused implementation.  
	 * The problem is that the wait/notifyAll are declared in completely different objects,
	 * so the monitor/lock which each runner thread holds is never informed via the notifyAll.<br/>
	 * It's here just for educational purposes.
	 * Using the <code>MUTEX</code> object was me mixing unrelated synchronization idioms, probably.
	 */
	@Ignore(value="See comments")
	public void testGuardedBlock_v1() {
		
		for(int i = 1; i <= 10; i++){
			
			XThread thread = new XThread(new GuardedRunnable("ExecutorServiceTest.Thread " + i));
			thread.setDaemon(false);
			thread.start();
		}
		

		Thread thread = new Thread(new Runnable(){

			public void run() {
				
				while(countdown.incrementAndGet() < (MAX_COUNT + 1000)) {
					System.out.println(countdown.get());
				}
				
				notifyAllThreads(); 
			}
			
		});
		thread.setDaemon(false);
		thread.start();
		
		try {
			// JOIN makes the current thread wait until the target thread has finished, or reached the limit in ms.  
			// Not an intuitively-named method. 
			thread.join(10000);    
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Quitting...");
	}
	
	// this is actually synchronized on the test class itself.
	private synchronized void notifyAllThreads() {

		notifyAll();  
	}	

	
	private final static AtomicInteger countdown = new AtomicInteger();
	private final int MAX_COUNT = 10000;
	final Object MUTEX = new Object();
	
	class GuardedRunnable implements Runnable {
		
		String threadName;
		
		public GuardedRunnable(String threadName) {
			super();
			this.threadName = threadName;
		}

		public void run() {
			
			while(countdown.get() < MAX_COUNT){
				spin();						
			}
			
			System.out.println(threadName + " is finished.");
		}
		
		private void spin() {
			try {
				
				synchronized(MUTEX){
					wait();
				}
			} catch (InterruptedException e) {
				System.err.println(threadName + " was interrupted.");
			}			
		}
	}	
	
}
