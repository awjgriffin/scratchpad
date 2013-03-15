package projecteuler;

import org.junit.Test;

/**
 * @see http://projecteuler.net/problems
 * 
 * @author grandre
 */
public abstract class EulerProblem {

	@Test
	public void testProblem() { 
		
		System.out.println("Problem description:\n" + problemDescription() + "\n\n");

		timedSolution();
		
	}; 
	

	protected void timedSolution() {
		
		long startTime = System.currentTimeMillis();

		System.out.println("Solution:\n");				

		solution();
		
		System.out.println(String.format("Solution took %s to complete.", calcTime(startTime) ) );
	}
	
	private String calcTime(long startTime) {
		
		long ms = System.currentTimeMillis() - startTime;
		return (ms < 1000) ? ms + " ms" : (ms/1000) + " s";
	}

	/**
	 * This should be overridden with the actual computation/algorithm etc.
	 * It should <code>System.out.println</code> the solution also.
	 */
	public abstract void solution();
	public abstract String problemDescription();
	
}
