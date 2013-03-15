package utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utils.ClassUtils.inheritsFrom;
import static utils.ClassUtils.listInterfaces;

import java.io.Serializable;
import java.util.Iterator;

import org.junit.Test;

public class ClassUtilsTests {

	@SuppressWarnings("serial")
	class SuperSampleClass implements Serializable {}
	
	@SuppressWarnings({ "serial", "rawtypes" })
	class SampleClass extends SuperSampleClass implements Comparable, Iterable {

		public Iterator iterator() {
			return null;
		}

		public int compareTo(Object o) {
			return 0;
		}
	}
	
	
	@Test
	public void testContainsInterfaces() {
		
		assertTrue(inheritsFrom(SampleClass.class, Comparable.class));
		
		assertFalse(inheritsFrom(SampleClass.class, Runnable.class));
		
		listInterfaces(SampleClass.class);
	}
}
