package utils;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import selfstudy.fp.Function;
import selfstudy.fp.Predicate;
import selfstudy.fp.Reduceable;

public class IteratorFunctionalUtils {
	
	/**
	 * @return an Iterator of monotonically increasing integers starting at 1 and ending at <code>Integer.MAX_VALUE</code>.
	 */
	public static Iterator<Integer> integers() {
		return naturals(Integer.MAX_VALUE);
	}
	
	/**
	 * @param max
	 * @return an Iterator of monotonically increasing naturals starting at 1 and ending at <code>max</code>.
	 */
	public static Iterator<Integer> naturals(final int max) {
		
		return new Iterator<Integer>(){

			int i = 1;
			
			public boolean hasNext() {
				return max > 0 && i <= max;
			}

			public Integer next() {
				return i++;
			}

			public void remove() {
			}
			
		};
		
	}
	
	/**
	 * @return an Iterator of monotonically increasing longs starting at 1 and ending at <code>Long.MAX_VALUE</code>.
	 */
	public static Iterator<Long> longs() {
		return longs(Long.MAX_VALUE);
	}
	
	/**
	 * @param max
	 * @return an Iterator of monotonically increasing longs starting at 1 and ending at <code>max</code>.
	 */
	public static Iterator<Long> longs(final long max) {
		
		return new Iterator<Long>(){

			long i = 1;
			
			public boolean hasNext() {
				return max > 0 && i <= max;
			}

			public Long next() {
				return i++;
			}

			public void remove() {
			}
			
		};
		
	}	
	
	
	/**
	 * @param nthTerm the number of terms to calculate
	 * @return An array of Integers containing <code>nthTerm</code> fibonacci terms.
	 */
	public static Integer[] fibonacciTermsArray(final int nthTerm) {
		
		Integer[] terms = new Integer[nthTerm];
		terms[0] = 0;
		terms[1] = 1;
		
		for(int idx = 2; idx < nthTerm; idx++){
			terms[idx] = terms[idx-1] + terms[idx-2];
		}
		
		return terms;
	}
	
	/**
	 * @param maxNum the upper limit for calculated fibonacci terms.
	 * @return an Iterator of fibonacci terms, including terms up to the value of <code>maxNum</code>.
	 */
	public static Iterator<Long> fibonacciNumbersLessThan(final int maxNum) {
		
		return new Iterator<Long>() {
		
			Long[] terms = new Long[2];
			long next = 0;
			int counter = 0;
			
			public boolean hasNext() {
				return (next = getNext()) <= maxNum;
			}

			public Long next() {
				counter++;
				return next;
			}

			private Long getNext() {
				
				terms = expandArray(terms);
				return terms[counter] = counter < 2 ? counter : terms[counter - 1] + terms[counter - 2];
			}
			
			public void remove() {
			}
			
		};
		
	} 	
	
	/**
	 * @param array the input array.
	 * @return an Iterator over the elements of <code>array</code>
	 */
	public static <T> Iterator<T> toIterator(final T[] array) {
		
		return new Iterator<T>() {
			
			int idx = 0;
			
			public boolean hasNext() {
				return idx < array.length;
			}

			public T next() {
				return array[idx++];
			}

			public void remove() {
			}
			
		};
		
	} 

	public static boolean isPrime(Long integer) {
		
		return getFactors(integer).length == 2;
	}
	
	public static Iterator<Long> primes(final long max) {
		
		return new Iterator<Long>() {

			Iterator<Long> longs = longs(max);
			Long next = null;
			
			Long [] knownPrimes = {};
			
			/*
			 * Trial division, not sieve of Erathosthenes
			 * Optimally you test only with known primes smaller than the square root of the candidate.
			 * Collect square roots that are prime.
			 */
			private boolean prime(long n) {
				
				if(knownPrimes.length == 0) {
				
					Double sqrRoot = sqrRoot(n);
					
					if(isPrime(sqrRoot.longValue())) {
						updatePrimes(n);
					}
					
				} 
					
				for(int i = 0; i < knownPrimes.length; i++) {
					
					if(n % knownPrimes[i] == 0) {
						return false;
					} else {
						
						boolean prime = isPrime(n);
						if(prime) {
							updatePrimes(n);
						}
						
						return prime;
					}
				}
				
				return false;
			}
			
			public void updatePrimes(long n) {
				knownPrimes = expandArray(knownPrimes);
				knownPrimes[knownPrimes.length - 1] = n;
			}
			
			public boolean hasNext() {
				
				while(longs.hasNext()) {
					next = longs.next();
					if(prime(next)){	return true; }
				}
				
				return false;
			}

			public Long next() {
				return next;
			}

			public void remove() {
			}
		};
		
	}
	
	public static Long[] getFactors(Long num) { 

		Long[] factors = new Long[1];
		factors[0] = 1L;

		if(num == 1) { return factors; }
		
		long max = num;
		for(long i = 2; i < max; i++) {
			
			if(num % i == 0){
				long result = num / i;
				factors = expandArray(factors, 2);
				factors[factors.length-2] = i;
				factors[factors.length-1] = result;
				max = result;
			}
			
		}
		
		factors = expandArray(factors);
		factors[factors.length-1] = num;
		
		return factors;
	}

	public static Long[] expandArray(Long[] in) {
		return expandArray(in, 1);
	}
	
	public static Long[] expandArray(Long[] in, int byHowMany) {
		Long[] oldArray = in;
		in = new Long[in.length + byHowMany];
		System.arraycopy(oldArray, 0, in, 0, oldArray.length);
		return in;
	}
	
	
	public static boolean isPalindrome(Long num) {
		boolean isPalindrome = true;
		char[] digits = num.toString().toCharArray();
		for (int i = 0; i < (digits.length / 2); i++) {
			isPalindrome &= (digits[i] == digits[digits.length - (i+1)]);
		}
		return isPalindrome;
	}

	public static boolean isEvenlyDivisible(long num, int max) {
		
		boolean isEvenlyDivisible = (num != 0);
		
		for(int i = 1; i <= max; i++){
			isEvenlyDivisible &= ((num % i) == 0);
		}
		
		return isEvenlyDivisible;
	}
	
	public static long sumOfSquares(long max) {
		long sum = 0;
		Iterator<Long> longs = longs(max);
		while(longs.hasNext()) {
			Long next = longs.next();
			sum += (next * next);
		}
		return sum;
	}
	
	public static long squareOfSum(long max) {
		long sum = 0;
		Iterator<Long> longs = longs(max);
		while(longs.hasNext()) {
			sum += longs.next();
		}
		return sum * sum;
	}	

	public static boolean isPythagoreanTriplet(long a, long b, long c) {
		
		if(c < b || b < a) { return false; }
		else {
			return ((a * a) + (b * b)) == (c * c);
		}
	}
	
	public static double sqrRoot(double n) {
		return sqrRoot(n, 1d);
	}

	private static double sqrRoot(double n, double guess) {
		
		double tolerance = .0001d;
		double quotient = (n / guess);
		double d = avg(quotient, (double) guess);
		
		if((abs(n - (d * d))) > tolerance){
			return sqrRoot(n, d);
		} else {
			return d;
		}
		
	}
	
	public static double avg(Double... d) {
	
		double sum = 0;
		for (int i = 0; i < d.length; i++) {
			sum += d[i];
		}
		
		return sum / d.length;
	}
	
	public static double avg(Long... l) {
		
		Double [] d = new Double[l.length];
		for (int i = 0; i < l.length; i++) {
			d[i] = new Double(l[i]);
		}
		
		return avg(d);
	}
	
	
	public static double abs(double d) {
		return d < 0 ? (- d) : d;
	}
	
	
	public static long gcd(long a, long b) {

		return (a % b == 0) ? b : gcd(b, (a % b));
		
	}
	
	public static long square(long in){	return in * in;	}
	
	public static double stdDev(Long... input){
		
		// get average of input
		// get squares of (each input element - average)
		// average those values
		// take square root
		
		double initialAverage = avg(input);
		Long [] avgs = new Long[input.length];
		for (int i = 0; i < avgs.length; i++) {
			avgs[i] = square(input[i] - new Double(initialAverage).longValue());
		}
		
		double averageOfDifferences = avg(avgs);
		
		return sqrRoot(averageOfDifferences);
	}


	public static Long multiplyLongs(Long... in) {

			return reduce(MultiplyLongs, 1L, arrayIterator(in));
	};


	private static Reduceable<Long,Long> MultiplyLongs = new Reduceable<Long, Long>() {
		
		public Long eval(Long d, Long s) {
			return d.longValue() * s.longValue();
		}
	};
	
	public static <S,D> Iterable<D> map(final Function<S, D> f, final Iterable<S> targets) {
		
		return new Iterable<D>() {

			public Iterator<D> iterator() {
				
				return new Iterator<D>() {

					Iterator<S> it = targets.iterator();
					
					public boolean hasNext() {
						return it.hasNext();
					}

					public D next() {
						return f.eval(it.next());
					}

					public void remove() {}
					
				};
			}
			
		};
	}
	
	
	public static <S, D> D reduce(final Reduceable<D,S> r, D init, final Iterable<S> targets) {

		D result = init;
		
		Iterator<S> iterator = targets.iterator();
		
		while(iterator.hasNext()) {
			result = r.eval(result, iterator.next());
		}
		
		return result;
	}
	
	
	public static <T> Iterable<T> filter(final Predicate<T> pred, final Iterable<T> in){

		return new Iterable<T>() {

			public Iterator<T> iterator() {
				
				return new Iterator<T>(){

					Iterator<T> source = in.iterator(); 
					
					T next = null;
					
					public boolean hasNext() {
						
						while(source.hasNext()){

							T t = source.next();
							if(pred.apply(t)){
								next = t;
								return true;
							}
						}
						
						return false;
					}

					public T next() {
						return next;
					}

					public void remove() {
					}
					
				};

			}
			
		};
	}
	
	public static Predicate<Character> isBinaryDigit = new Predicate<Character>(){

		public Boolean apply(Character in) {
			//TODO: Unicode or query the default character encoding
			return in.equals('0') || in.equals('1');   
		}
	};
	
	
	public static <T> Iterable<T> arrayIterator(final T[] in ) {

		return new Iterable<T>(){

			public Iterator<T> iterator() {
						
				return new Iterator<T>(){

					int i = 0;
					
					public boolean hasNext() {
						return i < in.length;
					}
	
					public T next() {
						return in[i++];
					}
	
					public void remove() {
					}
					
				};
			}
			
		};
		
	}

	public static String toBinaryString(int in){
		return toBinaryString(new Long(in));
	}

	
	public static String toBaseString(Long in, int base){
		
		if(base < 2) {return "Only bases greater than binary supported.";}
		else {
			return "";
		}
	}
	
	
	public static String toBinaryString(Long in) {

		String s = "";
		for(long i = in; i > 1; i /= 2) {
			s = (i % 2) + s;
		}
		
		return in.longValue() == 0 ? "0" : s;
	}

	
	public static boolean isBinaryString(String in) { 
	
		Iterable<Character> filter = filter(isBinaryDigit, toIterable(toCharIterator(in)));
		int count = count(filter.iterator());
		
		return count == in.length();
	}
	
	/**
	 * Consuming.
	 * TODO: create a non-consuming count function.
	 * @param in
	 * @return the number of items in the {@link Iterator} <code>in</code>.
	 */
	public static int count(Iterator<?> in){

		List<Object> l = new LinkedList<Object>();
		int i = 0;
		
		while(in.hasNext()) {
			l.add(in.next());
			i++;	
		}
		
		return i;
	}
	
	
	public static <T> Iterable<T> toIterable(final Iterator<T> in){
		
		return new Iterable<T>(){

			public Iterator<T> iterator() {
				return in;
			}
		};
	}
	
	
	public static Iterator<Character> toCharIterator(final String in){

		final char[] charArray = in.toCharArray();
		
		return new Iterator<Character>() {
		
			int i = 0;
			
			public boolean hasNext() {
				return i < charArray.length;
			}

			public Character next() {
				return new Character(charArray[i++]);
			}

			public void remove() {
			}
			
		};
		
	}
	
	public static BigInteger factorial(Long n) {
		
		BigInteger x = new BigInteger("1");
		while(n > 0) {
			x = x.multiply(new BigInteger(n.toString()));
			n--;
		}
		return x;
	
	}

	 public static Iterator<Integer> ones(){
		 
		 return new Iterator<Integer>(){

			public boolean hasNext() {
				return true;
			}

			public Integer next() {
				return 1;
			}

			public void remove() {
			}
			 
		 };
	 }
	
}