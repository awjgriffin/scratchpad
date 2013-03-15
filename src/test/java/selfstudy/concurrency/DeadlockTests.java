package selfstudy.concurrency;

import org.junit.Ignore;

public class DeadlockTests {

	
	
	class Getter {
		
		private boolean got;
		
		public synchronized boolean get(Getter other) {
			while(!other.got()) {
				other.get(this);
			}
			got = true;
			return got;
		}
		
		public boolean got() {return got;}
	}
	
	//TODO: create a backout policy/timer
	@Ignore(value="this isn't quit right, see the example here: http://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html")
	public void testCreateDeadlock() {
		
		final Getter A = new Getter();
		final Getter B = new Getter();
		
		new Thread(new Runnable(){
			public void run() {
				A.get(B);
			}}).start();
		
		new Thread(new Runnable(){
			public void run() {
				B.get(A);
			}}).start();
		
		
	}
}
