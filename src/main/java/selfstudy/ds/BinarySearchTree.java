package selfstudy.ds;

public class BinarySearchTree<T extends Comparable<T>> {

	protected BinarySearchNode<T> root = null;

	public BinarySearchTree(T rootItem) {
		super();
		this.root = new BinarySearchNode<T>(rootItem);
	}
	
	public BinarySearchTree() {
		super();
	}

	public boolean isEmpty() {
		return this.root == null;
	}
	
	public int size() {
		return isEmpty() ? 0 : root.size();
	}

	public void add(T item) {

		if(isEmpty()) {
			this.root = new BinarySearchNode<T>(item);
		} else {
			root.add(item);
		}
	}
	
	/**
	 * @return The maximum depth
	 */
	public int depth() {
		return isEmpty() ? 0 : root.depth();
	}
	
	//TODO
	public String visualiseTree() {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	
	//TODO: find/contains?
	
	//TODO
	public LinkedList<T> toList(){

		return root.aggregate();
	}
	
	
	/**
	 * @return True if number of Lefts (arbitrary choice) is between 35% and 75% of total size.
	 */
	public boolean isBalanced() {
		
		float f = (float) root.getNumberOfLefts() / (float) size();
		return (size() == 0 || (f > .35f && f < .75)) ? true : false;
	}
	
}
