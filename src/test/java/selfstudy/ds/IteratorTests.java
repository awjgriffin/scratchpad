package selfstudy.ds;

import static org.junit.Assert.*;

import org.junit.Test;

public class IteratorTests {

	@Test
	public void testReset() {
		
		LinkedList<String> stringList = LinkedListTests.testAdd();

		Iterator<String> iterator = stringList.iterator();
		
		testHasNext(iterator);
		iterator.reset();
		testHasNext(iterator);		
		
	}
	
	private void testHasNext(Iterator<?> iterator) {
		
		while(iterator.hasNext()) {
			assertNotNull(iterator.forward());
		}
	}

	/**
	 * With an iterator of 1 item, peek 10 times, then try hasNext()
	 */
	@Test
	public void testPeek() {
		
		Iterator<Class<?>> iterator = new LinkedList<Class<?>>().add(IteratorTests.class).iterator();
		
		for (int i = 0; i < 10; i++) {
			assertNull(iterator.peek());
		}
		
		assertTrue(iterator.hasNext());
		iterator.forward();
		assertFalse(iterator.hasNext());
	}
	
}
