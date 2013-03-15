package projecteuler;

import org.junit.Test;

public abstract class EulerProblem {

	@Test
	public void testProblem() { 
		
		System.out.println("Problem description:\n" + problemDescription() + "\n\n");
		System.out.println("Answer:\n");
		
		solution();
		
	}; 

	public abstract void solution();
	public abstract String problemDescription();
	
}
