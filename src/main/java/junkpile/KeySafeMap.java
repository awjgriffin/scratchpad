package junkpile;

import java.util.HashMap;
import java.util.Map;

/**
 * A very domain-specific map with a limited number of public, known keys.<br/>
 * Does not allow any put operations with any other keys.
 * @author grandre
 */
public class KeySafeMap<K, V> extends HashMap<String, V> {

	public KeySafeMap(String...keys) {

		for(String key : keys) {  		put( key, null );   		}
	}

	@Override
	final public V put(String key, V value) {
		return containsKey( key ) ? put( key, value ) : value;
	}

	@Override
	final public void putAll(Map<? extends String, ? extends V> m) {

		for(String key : m.keySet()) {
			put( key, m.get( key ) );
		}
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		for( String key : keySet() ) {
			sb.append( key ).append(": ").append( get( key ) ).append("\n");
		}
		return sb.toString();
	}

	
//	public final static String LAST_RELEASE_NUMBER = "lastReleaseNumber", MESSAGE = "message" ;
	
/*	
	stats.put("newReleaseCount", "0");
	stats.put("newCommitCount", "0");
	stats.put("message"	
	*/
	
}
