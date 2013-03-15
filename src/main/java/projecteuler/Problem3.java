package projecteuler;

import static utils.IteratorFunctionalUtils.*;

import java.util.Iterator;

public class Problem3 extends EulerProblem {
	
	@Override
	public void solution() {
		
		Iterator<Long> iterator = toIterator(getFactors(600851475143L));
		
		long maxPrimeFactor = 0;
		
		while(iterator.hasNext()) {
			Long next = iterator.next();
			
			if(isPrime(next)) {
				maxPrimeFactor = Math.max(next, maxPrimeFactor);
			}
		}
		
		System.out.println(maxPrimeFactor);
	}

	@Override
	public String problemDescription() {
		return "The prime factors of 13195 are 5, 7, 13 and 29.\n" + 
				"What is the largest prime factor of the number 600851475143 ?";
	}

	
}
