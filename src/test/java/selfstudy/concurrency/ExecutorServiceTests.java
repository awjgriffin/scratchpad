package selfstudy.concurrency;

import static org.junit.Assert.assertNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * Threads are lightweight processes; they are the basic unit of scheduling in a multi-processor system.
 * 
 * 
 * 
 * @author grandre
 *
 */
public class ExecutorServiceTests {

	Callable<String> endlessRunnable = new Callable<String>(){

		public String call() throws Exception {
			
			while(true) {
				// this is bogus and wastes cycles; wait() should be used.
				// however, using wait() returns a Future when the CompletionService.poll() is called.
				// wait();
			}
		}
		
	}; 
			

	
    /**
	 * Completion Services combines and Executor service and a queue which holds completed tasks.
     * @throws InterruptedException 
	 */
	@Test
	public void testPollEndlessTask() throws InterruptedException {
		
		Executor executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		
		CompletionService<String> service = new ExecutorCompletionService<String>(executor);
		
		service.submit(endlessRunnable);
		
		Thread.sleep(100);
		
		assertNull(service.poll());
	
		// service.take();    //this waits until the Completion Service adds a completed task to the "completed" queue.
	}

	
	
}
