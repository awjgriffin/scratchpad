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

	@Override
	public String problemDescription() {
		return "2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder." + 
				"What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?";
	}
	
}
