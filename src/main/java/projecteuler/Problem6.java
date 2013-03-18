package projecteuler;

import static utils.IteratorFunctionalUtils.*;

public class Problem6 extends EulerProblem {

	@Override
	public void solution() {
		System.out.println(squareOfSum(100) - sumOfSquares(100));
	}

}
