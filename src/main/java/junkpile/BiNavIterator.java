package junkpile;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Simple implementation of {@link ListIterator}.<br/>
 * Does not allow &quot;wrap-around&quot; iteration.
 * 
 * TODO: does not work!  
 * Originally wanted something that could get next() and previous() as a pair, but then what do you set the index to?
 * It never advances.
 * Better to implement some tuple and advance the iterator by two
 * 
 * @author grandre
 *
 * @param <E>
 */
public class BiNavIterator<E> implements ListIterator<E> {

	private final List<E> list;
	private int index = 0;
	
	public BiNavIterator(List<E> list) {
		super();
		this.list = list;
	}

	@Override
	public boolean hasNext() {
		
		if( index < list.size() ) {
			return true;
		} else {
			index = (list.size() - 1);
			return false;
		}
	}

	@Override
	public E next() {
		
		if(hasNext())
			return list.get( index++ );
		else
			throw new NoSuchElementException();
	}

	@Override
	public boolean hasPrevious() {
		
		if ( index >= 0 ) {
			return true; 
		} else { 
			index = 0;
			return false;
		}
	}

	@Override
	public E previous() {
		
		if(hasPrevious())
			return list.get( index -- );
		else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public int nextIndex() {
		
		return Math.min( (index + 1), (list.size() -1) );
	}

	@Override
	public int previousIndex() {
		
		return Math.max( (index -1), 0 );
	}

	@Override
	public void remove() {
		// not supported
	}

	@Override
	public void set(E e) {
		// not supported		
	}

	@Override
	public void add(E e) {
		// not supported		
	}
	
}
