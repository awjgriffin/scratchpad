package selfstudy.concurrency.testobjects;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplicitLockObject implements RunnableSharedObject {

	boolean isFree = false;

	Lock lock = new ReentrantLock();

	// conditions have an intrinsic association with a lock:
	// See: http://docs.oracle.com/javase/6/docs/api/index.html?java/util/concurrent/locks/Condition.html
	Condition isFreeCondition = lock.newCondition();			
	
	// note: NOT synchronized!
	public void use(String threadname) {
		
		lock.lock();
		try {
			System.out.println( threadname + " waiting to acquire lock..." );
			
			while(!isFree) {
				
				try {
					isFreeCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println( threadname + " got lock, working..." );
		} finally {
			relinquish();
			lock.unlock();
		}
	}
	
	// note: NOT synchronized!	
	public void relinquish() {

		lock.lock();
		try {
			isFree = true;
			isFreeCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}		
		
	
}
