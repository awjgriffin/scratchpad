package projecteuler;

import static utils.NumberUtils.*;

public class Problem14 extends EulerProblem {

	@Override
	public void solution() {

		long num = 0;
		long[] numTerms = {};
		
		for(long candidate = 999999; candidate > 0; candidate--) {

			long[] collatzTerms = getCollatzTerms(candidate);
			if(collatzTerms.length > numTerms.length){
				numTerms = collatzTerms;
				num = candidate;
			}  
		}

		System.out.println(String.format("The number is %d, with %d terms: %s", num, numTerms.length, arrayToString(numTerms)));
		
	}

}
