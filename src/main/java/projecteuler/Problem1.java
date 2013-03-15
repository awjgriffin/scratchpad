package projecteuler;

import static utils.IteratorFunctionalUtils.naturals;

import java.util.Iterator;

public class Problem1 extends EulerProblem {

	@Override
	public void solution() {
		
		int sum = 0;
		
		Iterator<Integer> integers = naturals(999);
		
		while(integers.hasNext()) {
			Integer integer = integers.next();
			if((integer % 3 == 0) || (integer % 5 == 0)){
				sum += integer;
			}
		}
		
		System.out.println(sum);
		
	}

	@Override
	public String problemDescription() {
		return "If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.\n" +
				"The sum of these multiples is 23.\n" +
				"Find the sum of all the multiples of 3 or 5 below 1000.";
	}
	
}
