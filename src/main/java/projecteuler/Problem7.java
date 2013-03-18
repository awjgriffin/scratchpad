package projecteuler;

import java.util.Iterator;

import static utils.IteratorFunctionalUtils.*;

public class Problem7 extends EulerProblem{

	public void solution(){

		int nthPrime = 0;
		
		Iterator<Long> longs = longs();
		
		while(longs.hasNext()) {
			Long next = longs.next();
			
			if(isPrime(next)) {
				System.out.println(next);
				nthPrime++;
				if(nthPrime == 10001) {
					System.out.println("10,001 prime number is: " + next);
					break;
				}				
			}
		}
	}

}
