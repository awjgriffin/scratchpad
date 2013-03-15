package projecteuler;

import static utils.IteratorFunctionalUtils.*;

import java.util.Iterator;

public class Problem10 extends EulerProblem {

	@Override
	public void solution() {

		long sum = 0;
		Iterator<Long> primes = primes(2000000L);
		while(primes.hasNext()){
			sum += primes.next();
		}
		System.out.println(sum);
	}

	@Override
	public String problemDescription() {
		return "The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17." + 
				"Find the sum of all the primes below two million.";
	}

}