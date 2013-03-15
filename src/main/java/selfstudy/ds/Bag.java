package selfstudy.ds;

/**
 * A simple container with add but no remove methods.
 * @author grandre
 *
 * @param <T>
 */
public class Bag<T extends Comparable<T>> {
	
	// fixed-array implementation, when gets to 75%, double the size.
	
	Object[] elements;

	public Bag() {
		super();
		elements = new Object[10]; 
	}

	public Bag(T[] elements) {
		super();
		this.elements = elements;
		
	}
	
	public void add(T item) {
		
		if(percentageFilled() > .75) {
			Object[] newElements = new Object[(elements.length * 2)];
			System.arraycopy(elements, 0, newElements, 0, elements.length);
			elements = newElements;
		}

		elements[getFirstFree()] = item;
	}
	
	/*
	 * Returns the index of the first null element in the array. 
	 */
	private int getFirstFree() {
		
		for (int i = 0; i < elements.length; i++) {
			if(elements[i] == null) return i;
		}
		return -1;
	}
	
	public boolean isEmpty() {
		return (elements != null && elements.length == 0);
	}
	
	public int size() {
		return isEmpty() ? 0 : getNumberOfElements(); 
	}
	
	
	private Float percentageFilled(){

		if(!isEmpty()) {
			return ((float)getNumberOfElements() / (float)elements.length); 
		}
		return 0f;
	}
	
	private int getNumberOfElements() {
		
		int num = 0;
		for(int i = 0; i < elements.length; i++){
			num += (elements[i] == null) ? 0 : 1;
		}
		
		return num;
	}
}
