package projecteuler;

import static utils.IteratorFunctionalUtils.*;

public class Problem4 extends EulerProblem {

	public void solution() {
		
		long max = 0;
		for(int i = 100; i < 1000; i++) {
			for(int j = 100; j < 1000; j++) {
				long product = i * j;
				if(isPalindrome(product)) {
					max = Math.max(max, product);
				}
			}		
		}
		
		System.out.println(max);
	}

}
