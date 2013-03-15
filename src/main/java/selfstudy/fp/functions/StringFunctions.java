package selfstudy.fp.functions;

import selfstudy.fp.AbstractReduceable;
import selfstudy.fp.Function;

public class StringFunctions {

	public static Function<String, String> toUpper() {

		return new Function<String, String> (){
			public String eval(String source) {
				return source.toUpperCase();
			}
		};
	}
	
	public static Function<String, String> toLower() {

		return new Function<String, String> (){
			public String eval(String source) {
				return source.toLowerCase();
			}
		};
	}	
	
	
	// this makes a stupid public method; why would this be reduceable?  how would this be used in reduce?
	private static AbstractReduceable<String, Integer> substring(){
		
		return new AbstractReduceable<String, Integer>() {

			public String eval(String d, Integer s) {
				return d.substring(0, s.intValue());
			}
		};
	}

	/**
	 * Grab the first ten letters of the string 
	 * @return
	 */
	public static Function<String,String> firstTenLetters(){
		return substring().curry(10);
	}
	
	public static Function<String, String> SysOut() {
		
		return new Function<String, String> (){
			public String eval(String source) {
				System.out.println(source);
				return source;
			}
		}; 
	}
}
