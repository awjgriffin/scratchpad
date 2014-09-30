package selfstudy.ds;

import java.util.*;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

/**
 * A set implementing some basic wish-list functionality
 * 
 * @author grandre
 *
 * @param <T>
 */
public class CoolSet<T> extends AbstractSet<T> {

	final TreeSet<T> set;
	
	/**
	 * Default implementation is a TreeSet which sorts by natural order, if no Comparators are supplied.
	 * @param comparators
	 */
	public CoolSet(final Comparator<T>...comparators) {
		
		super();
		
		this.set = ArrayUtils.isEmpty( comparators ) ? new TreeSet<T>() : new TreeSet<T>(
				
			new Comparator<T>(){
				
				@Override
				public int compare(T o1, T o2) {
					return Ordering.compound( Lists.newArrayList( comparators )).compare(o1, o2);
				} });
	}
	
	/**
	 * Does not allow nulls.
	 */
	@Override
	public boolean add(T str) {

		if( str!= null) { 
			set.add(str);  
			return true;
		} else return false;
	}

	
	@Override
	public boolean addAll(Collection<? extends T> coll) {

		boolean allGood = true;
		
		for(T item : coll) {   		allGood &= add(item);  		}
	
		return allGood;
	}

	@Override
	public Iterator<T> iterator() {			return set.iterator(); 		}

	@Override
	public int size() { 			return set.size(); 		}
	
	
	@Override
	public String toString() {

		StringBuffer stringBuffer = new StringBuffer().append("");
		
		for(T s : set){  stringBuffer.append( s.toString() ).append( "\n" );  		}
		
		return stringBuffer.toString();
	}
	
	
	public static void main( String[] args ) {
		
		List stuff = new ArrayList();
		stuff.add("test");
		stuff.add(null);
		
		System.out.println( new CoolSet().addAll( stuff ) );
		
	}
	
}
