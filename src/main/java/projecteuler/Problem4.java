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

	@Override
	public String problemDescription() {
		return "A palindromic number reads the same both ways.\n" + 
				"The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99." + 
				"Find the largest palindrome made from the product of two 3-digit numbers.";
	}
	
}
