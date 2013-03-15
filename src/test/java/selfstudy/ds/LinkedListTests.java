package selfstudy.ds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

public class LinkedListTests {

	
	public static LinkedList<String> testAdd(){
		
		LinkedList<String> ll = new LinkedList<String>();
		assertTrue(ll.isEmpty());
		
		for(int x = 1; x <= 10; x++) {
			ll = ll.add(new String("ELEMENT-" + x));
			assertEquals(x, ll.size());
		}
		
		return ll;
	} 
	
	@Test
	public void testCopy() {
		
		LinkedList<String> list = testAdd();
		
		LinkedList<String> newList = list.add("New one");
		
		assertNotSame(list, newList);
	}
	
	
	@Ignore
	public void testGet(){
		
		LinkedList<String> linkedList = testAdd();
		
		System.out.println(linkedList.size());
		
		for(int i = 0; i < linkedList.size(); i++){
			System.out.println(linkedList.get(i));
		}
	}
	
	
	@Test
	public void testRemoveHead(){
		
		LinkedList<String> linkedList = testAdd();
		
		while(linkedList.size() > 0) {
			System.out.println(linkedList.remove(linkedList.head()) + " removed");
		}
	} 
	
	@Test
	public void testRemove(){
		
		LinkedList<String> linkedList = testAdd();
		
		int startingSize = linkedList.size();
		
		System.out.println(linkedList.remove(linkedList.get(new Random().nextInt(linkedList.size()))) + " removed");
		
		assertEquals((startingSize - 1), linkedList.size());
	}
	
	@Test
	public void testHead(){
		LinkedList<String> linkedList = testAdd();
		assertEquals(linkedList.head(), linkedList.get(0));
	} 
	
	@Test
	public void testTail(){
		
		LinkedList<String> linkedList = testAdd();
		
		int originalSize = linkedList.size();
		
		assertEquals(originalSize-1, linkedList.tail().size());
		
		assertEquals(originalSize, linkedList.size());

	}
	
	@Test
	public void testContains() {
		
		assertTrue(new LinkedList<String>().add("test").contains("test"));
		
		assertFalse(new LinkedList<String>().contains("test"));
	}

	
	@Test
	public void testToTree() {

		LinkedList<String> testAdd = testAdd();
		BinarySearchTree<? extends Comparable> tree = testAdd.toTree();
		System.out.println("Depth of tree: " + tree.depth() + ", size of tree: " + tree.size());
		
		
	}
	
}
