package projecteuler;

import java.util.Iterator;

import static utils.IteratorFunctionalUtils.*;

public class Problem9 extends EulerProblem {

	@Override
	public void solution() {

		boolean found = false;

		for(int a = 1; !found ; a++){
		
			for(int b = 2; !found ; b++){
				
				for(int c = (1000 - (a + b)); a + b + c <= 1000; c++){
					
					if((a + b + c == 1000) && isPythagoreanTriplet(a, b, c)){
						System.out.println(a * b * c);
						found = true;
					}
				}	
			}
		}
		
/*		Iterator<Long[]> tripletIterator = getTripletIterator(1000l);
		while(tripletIterator.hasNext()){
			Long[] next = tripletIterator.next();
			if(isPythagoreanTriplet(next[0], next[1], next[2])){
				System.out.println(next[0] + ", " + next[1] + ", " + next[2]);				
			}
		}*/
		
	}

	/**
	 * @param targetSum
	 * @return Returns an array of three ordered (each one larger than its predecessor) <code>Long</code> values which each add up to the <code>targetSum</code>.
	 */
	private Iterator<Long[]> getTripletIterator(final long targetSum) {
		
		return new Iterator<Long[]>() {
			
			long a = 1, b = a + 1, c = (targetSum - (a + b));
			
			public boolean hasNext() {
				return (a < b && b < c) && (a + b + c == targetSum);
			}

			public Long[] next() {
				Long[] longs = new Long[]{a,b,c};
				a-=2; b++; c++;
				return longs;
			}

			public void remove() {
			}
			
		};
		
	}

}
