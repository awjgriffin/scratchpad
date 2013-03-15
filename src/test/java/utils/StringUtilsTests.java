package utils;

import org.junit.Test;

import static org.junit.Assert.*;
import static utils.StringUtils.*;

public class StringUtilsTests {

	
	@Test
	public void testTernary(){
		
		assertEquals("First", iff("First", null));
		assertEquals("Second", iff(null,"Second"));
	}
}
