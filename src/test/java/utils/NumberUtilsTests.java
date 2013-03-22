package utils;

import org.junit.Test;
import static utils.NumberUtils.*;

public class NumberUtilsTests {

	@Test
	public void testCollatzTerms() {
		
		System.out.println( arrayToString( getCollatzTerms(13l) ) );
	} 
	
}
