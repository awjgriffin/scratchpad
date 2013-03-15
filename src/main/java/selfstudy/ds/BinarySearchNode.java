package selfstudy.ds;

import utils.FunctionalUtils;

public class BinarySearchNode<T extends Comparable<T>> {

	private T value;
	protected BinarySearchNode<T> left;
	protected BinarySearchNode<T> right;
	
	public BinarySearchNode(T value, BinarySearchNode<T> left,
			BinarySearchNode<T> right) {
		super();
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	public BinarySearchNode(T value) {
		super();
		this.value = value;
	}


	public T getValue() {
		return value;
	}

	public int size() {
		return 1 + (left == null ? 0 : left.size()) + (right == null ? 0 : right.size());
	}
	
	/**
	 * Equal objects overwrite existing values.
	 * @param item
	 */
	public void add(T item) {
		
		if(item.compareTo(getValue()) < 0) {
			
			// new node is "less than" this value, goes to the left
			if(left == null){
				left = new BinarySearchNode<T>(item);
			} else {
				left.add(item);
			}
			
		} else if(item.compareTo(getValue()) > 0) {

			// new node is "greater than" this value, goes to the right
			if(right == null){
				right = new BinarySearchNode<T>(item);
			} else {
				right.add(item);
			}				
			
		} else {
			this.value = item;  // equal will just overwrite existing
		}
			
	}
	
	protected int getNumberOfLefts() {
		
		int lValue = ((left == null) ? 0 : (1 + left.getNumberOfLefts())); 
		int rValue = ((right == null) ? 0 : (0 + right.getNumberOfLefts()));
		return lValue + rValue;
	}
	

	public String visualizeTree() {
		
		// TODO: assume 80 character width, limit each value's toString to 10 chars?
		// root value is centered in screen,
		// each node adds two vertical lines as the "trunk" to/from its parent node
		//
		StringBuffer sb = new StringBuffer();
		sb.append("TODO");
		return sb.toString();		
	}
	
	public int depth() {
		
		int lValue = (this.left == null) ? 0 : this.left.depth();
		int rValue = (this.right == null) ? 0 : this.right.depth();
		
		return 1 + Math.max(lValue, rValue);
	}

	
	//TODO v1 - INCORRECT - duplicates some elements
	public LinkedList<T> aggregate() {

		LinkedList<T> rightAggregate = new LinkedList<T>();
		LinkedList<T> leftAggregate = new LinkedList<T>();

		if(right == null) {
			rightAggregate = new LinkedList<T>().add(getValue());
		} else {
			rightAggregate = right.aggregate();
		}
		
		if(left == null) {
			leftAggregate = new LinkedList<T>().add(getValue());
		} else {
			leftAggregate = left.aggregate();
		}
		
//		return FunctionalUtils.append(leftAggregate, rightAggregate);
		return null;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinarySearchNode other = (BinarySearchNode) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
