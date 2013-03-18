package projecteuler;

import static utils.IteratorFunctionalUtils.*;

import java.util.Iterator;

/**
 * TODO: this takes forever...
 * 
 * 
 * @author grandre
 *
 */
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
	
}