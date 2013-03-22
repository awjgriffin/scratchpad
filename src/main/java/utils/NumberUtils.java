package utils;

import static utils.IteratorFunctionalUtils.expandArray;

public class NumberUtils {

	public static boolean isEven(long in) {  return in % 2 == 0;  	}

	public static boolean isOdd(long in)  {  return !isEven(in);  	}	
	
	public static long[] getCollatzTerms(long in) {
		
		long[] terms = new long[1];
		terms[0] = in;
		
		while(in > 1) {
			in = isEven(in) ? (in / 2) : ((3 * in) + 1);
			terms = expandArray(terms, 1, true);
			terms[terms.length-1] = in;
		}
		
		terms[terms.length-1] = 1l;

		return terms;
	}
	
	
	public static String arrayToString(long...longs){
		
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < longs.length; i++) {
			sb.append(longs[i]).append( i + 1 < longs.length ? "," : "]");
		}
		
		return sb.toString();
	}
	
}
