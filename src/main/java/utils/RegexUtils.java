package utils;

import java.util.regex.Pattern;

public class RegexUtils {

	
	public static Pattern getBetweenDelimiters(String d1, String d2, boolean inclusive){
		
		String inclusiveRegex = "(%s[^%s%s]*%s)"; 
		String exclusiveRegex = "%s([^%s%s]*)%s";

		return Pattern.compile(String.format((inclusive ? inclusiveRegex : exclusiveRegex), d1, d1, d2, d2));
		
	}
	
	
	
	// recursive implementation, which is great but doesn't preserve the original string, so if
	// token resolution fails half-way, you can only communicate the half-resolved origin string to the user.
	// matcher.find() provides the base case.
/*	protected static String resolve(String unResolvedString, Map<String, String> params) {

		Pattern compile = Pattern.compile();	
		
		Matcher matcher = compile.matcher(unResolvedString);
		
		while(matcher.find()){
			
			String paramAndVariableName = matcher.group(1);
			
			if(params.containsKey(paramAndVariableName)){
				return resolve(unResolvedString.replace("<" + paramAndVariableName +">", params.get(paramAndVariableName)), params);
			} else {
				throw new RuntimeException(String.format("Could not resolve label %s missing parameter: %s ", unResolvedString, paramAndVariableName));
			}
			
		}
		
		return unResolvedString;
	}*/
	
}