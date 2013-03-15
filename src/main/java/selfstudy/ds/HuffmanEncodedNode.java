package selfstudy.ds;

import utils.FunctionalUtils;

public class HuffmanEncodedNode<T extends Comparable<T>> extends BinarySearchNode<T> {

	public HuffmanEncodedNode(T value) {
		super(value);
	}
	
	protected int weight; 
	protected LinkedList<T> members; // aggregation of all members "below"
	
	protected HuffmanEncodedNode<T> left;
	protected HuffmanEncodedNode<T> right;
	
	/**
	 * @return Combined weight of all nodes &quot;below&quot; this one.
	 */
	public int getWeight() {
		
		return (left == null ? 0 : left.getWeight()) + (right == null ? 0 : right.getWeight());
	}

	/**
	 * @return Aggregated list of all members &quot;below&quot; this one.
	 */
	public LinkedList<T> getMembers() {
		
		return FunctionalUtils.append(left.getMembers(), right.getMembers());
	}
	
	
	public T find(String code){
		
		// if 0, go left.  if 1, go right.  If left and right are null, return value. 
		
		return null;
	}	
	
	
}
