package projecteuler;

import java.util.Iterator;

import static utils.IteratorFunctionalUtils.*;

public class Problem5 extends EulerProblem {
	
	/** 
	 * This takes 82 seconds to compute...
	 */
	@Override
	public void solution() {
		
		Iterator<Long> longs = longs();
		while(longs.hasNext()) {
		
			Long next = longs.next();
			if(isEvenlyDivisibleBy20(next)){
				System.out.println(next);
				break;
			}
		}
		
	}
	
	private boolean isEvenlyDivisibleBy20(long num) {
		return isEvenlyDivisible(num, 20);
	}

}
