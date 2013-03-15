package utils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A helper class of questionable value; mostly just to get around the non-builder behaviour of java.util.Set classes.
 * 
 * @author grandre
 *
 * @param <E>
 */
public class SetHelper<E> {

	private Set<E> set;

	public SetHelper(Set<E> set) {
		super();
		this.set = set;
	}
	
	public Set<E> addAll(Set<E> toAdd) {
		
		Set<E> newSet = new HashSet<E>();
		newSet.addAll(this.set);
		newSet.addAll(toAdd);
		return newSet;
	}

	public SortedSet<E> sort() {
		
		return new TreeSet<E>(set);
	}
	
	public SortedSet<E> sort(Comparator<E> comp) {
		
		SortedSet<E> set = new TreeSet<E>(comp);
		set.addAll(set);
		return set;
	}
	
}
