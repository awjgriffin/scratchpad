package selfstudy.fp;

import static selfstudy.fp.functions.NumericFunctions.addTen;
import static selfstudy.fp.functions.NumericFunctions.multiplyLongs;
import static selfstudy.fp.functions.NumericFunctions.square;
import static selfstudy.fp.functions.StringFunctions.SysOut;
import static selfstudy.fp.functions.StringFunctions.firstTenLetters;
import static selfstudy.fp.functions.StringFunctions.toUpper;
import static selfstudy.fp.predicates.StringPredicates.startsWith;
import static utils.FunctionalUtils.append;
import static utils.FunctionalUtils.display;
import static utils.FunctionalUtils.filter;
import static utils.FunctionalUtils.foreach;
import static utils.FunctionalUtils.last;
import static utils.FunctionalUtils.makeSet;
import static utils.FunctionalUtils.map;
import static utils.FunctionalUtils.reduce;
import static utils.FunctionalUtils.reverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import selfstudy.ds.LinkedList;

public class FunctionalUtilsTests {

	
	static LinkedList<String> stringList;
	static LinkedList<Long> longList;
	
	@BeforeClass
	public static void setup() {
		stringList = listOfStrings(10);
		longList = listOfLongs(10);
	}
	
	@Test
	public void testMap() {

		display( map(toUpper(), new LinkedList<String>().add("one").add("two").add("three").add("four")) );
		display( map(addTen(), new LinkedList<Long>().add(1L).add(2L).add(3L).add(4L).add(5L)));
		display( map(firstTenLetters(), new LinkedList<String>().add("Polymorphism").add("Commutativity").add("Encapsulation")) );
	} 

	@Test
	public void testReduce() {
		
		System.out.println("Reduction: " + reduce(multiplyLongs(), 1L, longList));
	}
	
	@Test
	public void testFilter() {
		
		display(filter(startsWith("t"), stringList));
	}
	
	@Test
	public void testMakeSet() {
		
		display(makeSet(new LinkedList<String>().add("one").add("four").add("two").add("three").add("four").add("four")));
	}
	
	@Test
	public void testLast() {
		
		display(last(stringList, 3));
	}
	
	@Test
	public void testReverse() {
		
		display(stringList.add("Original:\n"));
		display(reverse(stringList).add("Reversed:\n"));
	}	
	
	@Test
	public void testAppend() {
		
		display(append(new LinkedList<String>().add("one").add("two"), new LinkedList<String>().add("three").add("four")));
	}
	
	
	@Test
	public void testForEach() {
		
		foreach(SysOut(), stringList.add("For each:\n"));
	} 
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCompFunction() {
		
		display(map(new Comp<Long>(addTen(), square()), longList));
	}	

	
	private static LinkedList<Long> listOfLongs(int howMany) {
		
		LinkedList<Long> linkedList = new LinkedList<Long>();
		for (int i = 0; i <	howMany; i++) {
			linkedList = linkedList.add((long)i);
		}
		return linkedList;
	}
	
	private static LinkedList<String> listOfStrings(int howMany) {
		
		Vector<String> scrabbleWords = fileIntoVector("/OfficialScrabbleDictionary.txt");
		int size = scrabbleWords.size();
		
		LinkedList<String> linkedList = new LinkedList<String>();
		
		Random r = new Random();
		
		for(int i = 0; i < howMany; i++) {
			linkedList = linkedList.add(scrabbleWords.get(r.nextInt(size)));
		}
		
		return linkedList;
	}
	
	private static Vector<String> fileIntoVector(String filename){
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(FunctionalUtilsTests.class.getResourceAsStream(filename)));
		Vector<String> words = new Vector<String>();
		String s = ""; 
		
		try {
			
			while((s = bufferedReader.readLine()) != null) {
				words.add(s);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return new Vector<String>();
		}
		
		return words;
	}
}

