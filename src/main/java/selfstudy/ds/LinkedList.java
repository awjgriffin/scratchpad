package selfstudy.ds;

import java.util.NoSuchElementException;

import utils.ClassUtils;

/**
 * A cons-like list / FILO stack.  Single-direction only.
 * &quot;It's better to have 100 functions acting on one data type than 10 function acting on 10 data types.&quot - Alan J. Perlis(?);
 * 
 * TODO: currently the add/remove creates a full copy each time, which is very inefficient.  How to make it into a persistent collection? 
 * 
 * @author grandre
 *
 * @param <T>
 */
public class LinkedList<T> {
	
	protected Class<T> type;
	protected Node<T> head;
	
	/**
	 * CAR
	 * @return
	 */
	public Node<T> head() {
		return head;
	}
	
	/**
	 * CDR
	 * @return
	 */
	public LinkedList<T> tail() {
		
//		Mutable version				
//		this.remove(head);
//		return this;
		
//		Immutable/correct version		
		LinkedList<T> copy = copy(this);
		copy.remove(copy.head());
		return copy;
	}	
	
	/**
	 * Adds to the head, returns a new list
	 * @param o
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<T> add(T o) {

		//ver 1: mutable version
//		Node<T> n = new Node<T>(o, head);
//		head = n;
//		return this;
		
		//Ver 2 returns copy; immutable

		LinkedList<T> copy = copy(this);
		Node<T> n = new Node<T>(o, copy.head);
		copy.head = n;
		copy.type = (Class<T>) o.getClass(); // assigned anew, each time
		
		return copy;
		
	}	
	
	public Iterator<T> iterator() {
		
		return new Iterator<T>(){

			int index = 0;
			
			public T forward() {
				
				if(hasNext()){
					return get(index++).getValue();
				} else {
					throw new NoSuchElementException();
				}
			}

			public T back() {
				
				if((index - 1) >= 0){
					return get(index--).getValue();
				} else {
					throw new NoSuchElementException();
				}
			}

			public T last() {
				
				return get(size() - 1).getValue();
			}

			public T first() {
				
				return get(0).getValue();
			}

			public T peek() {
				
				return ((index + 1) <= (size() - 1)) ? get(index + 1).getValue() : null;
			}

			public void reset() {
				
				index = 0;
			}

			public boolean hasNext() {
				
				return get(index) != null;
			}
		};
	}
	
	/**
	 * TODO: Expensive! No good for large lists; make this a persistent data structure
	 * @param in
	 * @return a copy of the passed-in list
	 */
	private LinkedList<T> copy(LinkedList<T> in) {
		
		if(in.isEmpty()) {
			return new LinkedList<T>();
		} else {

			LinkedList<T> copy = new LinkedList<T>();
			
			for(int i = (size() - 1); i >= 0; i--) {
				Node<T> n = new Node<T>(in.get(i).getValue(), copy.head);
				copy.head = n;
			}
			
			return copy;			
		}
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	/**
	 * @param index
	 * @return The element at the <code>index</code>, null otherwise.
	 */
	public Node<T> get(int index) {
		
		if(!isEmpty() && index <= (size() - 1)){
			
			Node<T> n = head;
			for(int i = 0; i < index; i++) {
				n = n.next;
			}
			return n;
		}
		
		return null;
	}
	
	public int size() {
		
		if(isEmpty()) {
			
			return 0;
		} else {
			
			int size = 0;
			Node<T> n = head;
			while(n != null){
				size++;
				n = n.next;
			}
			
			return size;
		}
	}
	
	public boolean contains(T value) {
		
		Node<T> n = head;
		while(n != null){
			
			if(n.getValue().equals(value)){
				return true;
			} else {
				n = n.next;
			}
		}
		
		return false;
	}
	
	
	@SuppressWarnings("unchecked")
	public BinarySearchTree<? extends Comparable> toTree(){
		
		if(ClassUtils.inheritsFrom(type, Comparable.class)){
			 
			BinarySearchTree tree = new BinarySearchTree();
			
			for(int i = (size() - 1); i >= 0; i--) {
				
				tree.add((Comparable) get(i).getValue());
			}
			
			return tree;
			
		} else {
			
			throw new ClassCastException("Cannot create Binary Search Tree; the type of this list must extend java.lang.Comparable");
		}
	}
	
	
	public Node<T> remove(Node<T> node) {
		
		if(node != null && !isEmpty()){

			if(node.equals(head)){
				Node<T> tmp = head;
				head = head.next;
				return tmp;
			}
			
			Node<T> n = head;
			while(n != null) {
				if(node.equals(n.next)){
					Node<T> tmp = n.next;
					n.next = tmp.next;
					return tmp;
				} else {
					n = n.next;
				}
			}
			return null;
		} else {
			return null;
		}
		
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Iterator<T> iterator = iterator();
		while(iterator.hasNext()) {
			sb.append(iterator.forward().toString());
			sb.append(iterator.hasNext() ? ", " : "");
		}
		sb.append("}");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		LinkedList other = (LinkedList) obj;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
