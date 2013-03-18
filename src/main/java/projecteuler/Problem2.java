package projecteuler;

import static utils.IteratorFunctionalUtils.fibonacciNumbersLessThan;

import java.util.Iterator;

public class Problem2 extends EulerProblem{

	public void solution() {

		long sum = 0;
		Iterator<Long> iterator = fibonacciNumbersLessThan(4000000);
		
		while(iterator.hasNext()) {
			Long next = iterator.next();
			sum = (next % 2 == 0) ? (sum + next) : sum; 
		}
	
		System.out.println(sum);
	}
	
}
