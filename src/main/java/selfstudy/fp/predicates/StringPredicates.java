package selfstudy.fp.predicates;

import selfstudy.fp.Predicate;

public class StringPredicates {

	
	public static Predicate<String> startsWith(final String prefix) {
	
		return new Predicate<String>(){
			public Boolean apply(String object) {
				return object.startsWith(prefix);
			}
		};
	}
	
}
