package utils;

import static org.junit.Assert.*;

import org.junit.Test;
import static utils.RegexUtils.*;


public class RegexUtilsTests {

	@Test
	public void testGetBetweenDelimiters(){
		assertEquals("(<[^<>]*>)", getBetweenDelimiters("<", ">", true).pattern());
		assertEquals("<([^<>]*)>", getBetweenDelimiters("<", ">", false).pattern());
	}
	
}
