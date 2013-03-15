package selfstudy.ds;

import org.junit.Test;

public class BagTests {

	
	@Test
	public void testFilledPercentage(){
		
		Bag<String> bag = new Bag<String>();
		for(int i = 0; i < 50; i++){
			bag.add("Item " + i);
		}
	}
	
}
