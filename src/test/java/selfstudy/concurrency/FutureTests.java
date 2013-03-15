package selfstudy.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.Test;

import selfstudy.concurrency.testobjects.EndlessDoNothingTask;

public class FutureTests {

	
	/**
	 * Cancels a task by using the Future/Executor Service.
	 * @throws InterruptedException 
	 */
	@Test
	public void testFutureCancel() throws InterruptedException {
		
		ExecutorService service = Executors.newFixedThreadPool(5);  //only 1 thread is used....
		
		Future<?> future = service.submit(new EndlessDoNothingTask());
		 
		// The ExecutorService really handles it's tasks state, so 
		// Future.cancel() is only effective once (the first time).
		// So the target task will run once, be interrupted once, and in the case of a {@link EndlessDoNothingTask}, 
		// resume what it was doing indefinitely.
		// This loop is just for demonstration purposes.
		for(int i = 1; i <= 10; i++){
			Thread.sleep(100 * i);  
			future.cancel(true);
		}		
		
		Thread.sleep(1500);		//can't join (no exposed thread), so just pause briefly to allow the loop to run...
	}
	
	@Test
	public void testMakeCallable() throws Exception {
		
		Callable<Object> callable 
			= Executors.callable(new EndlessDoNothingTask()); // creates a Thread behind the scenes?
		
		//callable.call();  // This would block indefinitely
		Executors.newSingleThreadExecutor().submit(callable);
	}
	
	@Test
	public void testMakeFutureTask() throws Exception {
		
		FutureTask<Object> futureTask 
			= new FutureTask<Object>(Executors.callable(new EndlessDoNothingTask()));
		
//		futureTask.get()	// this would block indefinitely
		Executors.newSingleThreadExecutor().submit(futureTask);
		
	}	
	
}
