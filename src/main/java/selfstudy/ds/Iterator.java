package selfstudy.ds;

/**
 * A slightly more feature-ful iterator, with forward and back navigation but no removal feature.
 * @author grandre
 *
 * @param <T>
 */
public interface Iterator<T> {

	public boolean hasNext();
	
	/**
	 * @return Returns next element, if it exists.  Throws {@link NoSuchElementException} otherwise.
	 */
	public T forward();

	/**
	 * @return Returns previous element, if it exists.  Throws {@link NoSuchElementException} otherwise.
	 */	
	public T back();
	
	/**
	 * @return Last in the list/collection, not last as in &quot;previous&quot;.
	 */
	public T last();

	/**
	 * @return First element in the list/collection.
	 */	
	public T first();
	
	/**
	 * Looks ahead to the next element, without incrementing the counter.
	 * @return The next element, or <code>null</code> if no such method exists.
	 */
	public T peek();
	
	/**
	 * Resets the counter so this Iterator can be used again.
	 */
	public void reset(); //resets index to first
	
}
