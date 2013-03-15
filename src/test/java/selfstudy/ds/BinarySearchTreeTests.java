package selfstudy.ds;

import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FunctionalUtils.*;

public class BinarySearchTreeTests {

	
	public BinarySearchTree<String> testAdd(String [] keys) {
		
		BinarySearchTree<String> stringTree = new BinarySearchTree<String>();
		
		for (int i = 0; i < keys.length;) {
			stringTree.add(keys[i++]);
			assertEquals(i, stringTree.size());
		}
		
		return stringTree;
	}
	
	@Test
	public void testAll(){

		String [] keys1 = {"A","B","C"};
		String [] keys2 = {"D","X","Z","C","E","J","R","Q","I","A"};
		String [] keys3 = {"A","D","B","X","Z","C","E","Y","J","R","Q","I"};
		String [] keys4 = {"D","B","X","Z","C","E","Y","J","R","Q","I", "U","F","G","K", "A"};
		
		String [][] allKeys = {keys1,keys2,keys3,keys4};
		
		for (int i = 0; i < allKeys.length; i++) {
			
			BinarySearchTree<String> stringTree = testAdd(allKeys[i]);
	
			StringBuffer results = new StringBuffer();
			results.append("Results for: " + keyElements(allKeys[i])).append("\n");
			results.append("--------------------------").append("\n");
			results.append(stringTree.isBalanced() ? "Balanced tree" : "Unbalanced tree").append("\n");
			results.append("Size: " + stringTree.size()).append("\n");
			results.append("Depth: " + stringTree.depth()).append("\n");
			System.out.println(results.toString());
			
		}
	}
	
	
	@Test
	public void testAggregate() {
		
		BinarySearchTree<String> stringTree = testAdd(new String[]{"A","B","C"});

		display(stringTree.toList());
	}
	
	
	private String keyElements(String[] in) {
		
		StringBuffer out = new StringBuffer("{");
		
		for (int i = 0; i < in.length; i++) {
			out.append(in[i]).append((i+1) < in.length ? "," : "");
		}
		
		out.append("}");
		return out.toString();
	}
	
}
