package puzzles;

public class PT {

	/*
	 *    n!
	 * 	------
	 * 	i!(n-i)!
	 * 
	 * 1 --> the number chosen
	 * (a + b) --> 1 1
	 * (a + b)(a + b) --> a^2 + 2ba + b^2  --> 1 2 1
	 * (a + b)(a + b)(a + b) --> a^3 + 3ab + 3ba + b^3   --> 1 3 3 1
	 * (a + b)(a + b)(a + b)(a + b) --> a^4 + 4ab + 6ab + 4ba + b^4 --> 1 4 6 4 1
	 * ...
	 * 
	 * as reduction on a list?  List would be 0,1,2,3,4,5 (the level/depth)
	 * 
	 */
	
	 public double[][] getPascalsCoeffecients(int num, int depth) {
		 
		 double[][] pascalsCoefficients = new double[depth + 1][depth];
		 
		 int tmpDepth = 0;
		 
		 int a = num, b = num;
		 
		 pascalsCoefficients[tmpDepth++][0] = Math.pow(a, depth);
		 
		 while(tmpDepth < depth) {
			 
			 pascalsCoefficients[tmpDepth][0] = Math.pow(a, depth);
					 
			 tmpDepth++;					 
		 }
		 
		 return pascalsCoefficients;
	 }

}
