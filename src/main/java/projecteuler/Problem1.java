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

}
