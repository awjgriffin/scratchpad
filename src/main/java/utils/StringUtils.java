package utils;



public class StringUtils {

	/**
	 * A <code>null</code> test ternary-operator &quot;shortcut&quot;.
	 * @param firstChoice a value or expression which is the preferred return value 
	 * @param secondChoice a default value if the first is null
	 * @return firstChoice if not null, secondChoice otherwise
	 */
	public static <T> T iff(T firstChoice, T secondChoice){
		
		return (firstChoice == null) ? secondChoice : firstChoice;
	}

	
	/**
	 * Concats an arbitrary number of {@link String} arguments.
	 * @param strings
	 * @return
	 */
	public static String concat(String...strings) {
		
		StringBuilder sb = new StringBuilder();
		for(String string : strings) {
			sb.append(string);
		}
		
		return sb.toString();
	}
	
}
