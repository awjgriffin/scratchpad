package utils;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;
import java.util.TreeMap;

import org.junit.Test;

public class DefaultedMapTests {

	
	@Test
	public void testMapCreation(){
	
		DefaultedMap<String,String> map = DefaultedMap.createMap(new TreeMap<String,String>());

		DefaultedMap<String, String> proxy = map.getLoggingProxy();
		proxy.put("Test", "Value");
		
		assertTrue(Proxy.isProxyClass(proxy.getClass()));
	}
	
}
