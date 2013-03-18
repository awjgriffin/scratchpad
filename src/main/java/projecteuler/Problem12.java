package projecteuler;

import static utils.IteratorFunctionalUtils.*;


public class Problem12 extends EulerProblem {

	@Override
	public void solution() {
		
		Iterable<Long> triangles = toIterable(triangles());
		
		for(Long triangle : triangles){
			
			if(getFactors(triangle).length > 500) {
				System.out.println(String.format("%d is the first triangle number to have over 500 divisors.", triangle));
				break;
			}
		}
		
	}

}
