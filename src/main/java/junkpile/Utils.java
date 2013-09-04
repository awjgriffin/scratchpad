package junkpile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Utils {

/*	public static <T> T[] concat(T...types) {
		return types;
	}*/

	public static String[] concat(String...strings) {
		return strings;
	}	
	
	public static String[] concat(String[] tArray, String...strings) {
		
		List<String> list = new ArrayList<String>();
		
		String [] newArray = new String[tArray.length + strings.length]; 
		
		list.addAll(Arrays.asList(tArray));

		for (String string : strings) {
			list.add(string);
		}
		
		return list.toArray(newArray);
	}

	public static void main(String[] args) {
	
		String [] array = new String []{"1","2","3","4"};
		
		String[] strings = Utils.concat(array, "5,6,7".split(","));
		
		for(String s : strings) {
			System.out.println(s);
		}
	}	
	
}
