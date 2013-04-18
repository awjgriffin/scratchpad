package utils;
import java.math.BigInteger;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import selfstudy.fp.Function;

import static org.junit.Assert.*;
import static utils.IteratorFunctionalUtils.*;

public class IteratorFunctionalUtilsTests {

	@Test
	public void testFibonacciTerms() {
		printIterator(fibonacciNumbersLessThan(10000000));
	}
	
	
	@Test
	public void testGetFactors() {
		printIterator(toIterator(getFactors(600851475143L)));
	}
	
	@Test
	public void testIsPrime(){
		assertTrue(isPrime(17L));
		assertFalse(isPrime(4L));		
	}
	
	@Test
	public void testIsPalindrome() {
		assertTrue(isPalindrome(90009L));
		assertTrue(isPalindrome(900009L));
		assertFalse(isPalindrome(91230L));
	}
	
	@Test
	public void testPythagoreanTriplet() {
		assertTrue(isPythagoreanTriplet(3l,4l,5l));
		assertFalse(isPythagoreanTriplet(3l,4l,6l));
	}
	
	@Test
	public void testPrimes() {
		printIterator(primes(2000L));
	}	

	@Test
	public void testSqrRoot() {
		Double sqrRoot = sqrRoot(17);
		System.out.println(sqrRoot);
		System.out.println(sqrRoot(8));
		System.out.println(Math.sqrt(8d));
	}	
	
	@Test
	public void testGCD() {
		System.out.println(gcd(120, 33));
	}
	
	@Test
	public void testStdDev() {
		
		Long [] l = {2l,4l,4l,4l,5l,5l,7l,9l};
		System.out.println(stdDev(l));
	}

	@Test
	public void testMultiplier() {
		
		Long[] longs = new Long[]{1L,2L,3L,4L,5L,6L,7L,8L,9L,10L};
		Long testResult = 1L;
		for (int i = 0; i < longs.length; i++) {
			testResult *= longs[i];
		}
		assertEquals(testResult, multiplyLongs(longs));
	}
	
	@Test
	public void testToBinaryString() {
		
		int[] ints = new int[]{4,37,99,127,255,1,0};
		
		for (int i = 0; i < ints.length; i++) {
			System.out.println("Decimal:" + ints[i] + ", Binary: " + toBinaryString(ints[i]));	
		}
		
	}

	@Test
	public void testToBaseString() {
		
		long[] longs = new long[]{4,37,99,127,255,1,0};
		int base = 14;
		
		for (int i = 0; i < longs.length; i++) {
			System.out.println( String.format("Decimal: %d, base %d: %s", longs[i], base, toBaseString(longs[i], base)));	
		}
		
	}	
	
	@Test
	public void testCount() {
		 
		String testString = "testCount";
		assertEquals(testString.length(), count(toCharIterator(testString)));	
	}
	
	
	@Test
	public void testIsBinaryString() {
		
		assertTrue(isBinaryString("010101010111"));
		assertFalse(isBinaryString("01010105161"));
	}	

	@Test
	public void testFactorial() {
		
		Iterable<Long> arrayIterator = arrayIterator(new Long[]{-1l,0l,5l,100l});
		
		printIterator(map(new Function<Long, BigInteger>(){

			public BigInteger eval(Long in) {
				return factorial(in.longValue());
			}
			
		}, arrayIterator).iterator());
		
	}
	

	@Ignore(value="not self-terminating")
	public void testOnes() {
		printIterator(ones());
	}

	@Ignore(value="self-terminating but takes a while")
	public void testLongs() {
		printIterator(longs());
	}	
	
	@Test
	public void testTriangles() {
		printIterator(triangles());
	}		
	
	// --- HELPER METHODS ---------------------------------
	private void printIterator(Iterator<?> in) {
		new PrintUtils().printIterator(in);
	}
	
}
