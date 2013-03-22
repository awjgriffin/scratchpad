package utils;

import selfstudy.ds.BinarySearchTree;
import selfstudy.ds.LinkedList;
import selfstudy.fp.Function;
import selfstudy.fp.Predicate;
import selfstudy.fp.Reduceable;

/**
 * TODO: Exercises from "Functional Programming for Java Developers, Chapters 3"
 * 
 * 
 * @author grandre
 *
 */

public class FunctionalUtils {

	
//	TODO: move to LinkedList?
	public static <S,D> LinkedList<D> map(Function<S, D> function, LinkedList<S> source) {
		
		if(source.isEmpty()){
			return new LinkedList<D>();
		} else {
			return map(function, source.tail()).add(function.eval(source.head().getValue()));
		}
		
	} 
	
//TODO: move to LinkedList?
	public static <S, D> D reduce(Reduceable<D,S> function, D init, LinkedList<S> source) {

		if(source.isEmpty()){
			return init;
		} else {
			return function.eval(reduce(function, init, source.tail()), source.head().getValue());
		}		
	}
	
	public static <S> LinkedList<S> filter(Predicate<S> predicate, LinkedList<S> source) {
	
		if(source.isEmpty()){
			return new LinkedList<S>();
		} else {
			if(predicate.apply(source.head().getValue())) {
				return filter(predicate, source.tail()).add(source.head().getValue());	
			} else {
				return filter(predicate, source.tail());
			}
		}
		
	}
	
	public static <T> LinkedList<T> makeSet(LinkedList<T> in) {
		
		// does car exist in cdr?
		// if so, don't add to results
		if(in.isEmpty()) {
			return new LinkedList<T>();
		} else {
			return in.tail().contains(in.head().getValue()) ? makeSet(in.tail()) : makeSet(in.tail()).add(in.head().getValue());   
		}
		
	}

	
	public static <T> LinkedList<T> last(LinkedList<T> in, int number) {
		LinkedList<T> out = in;
		while(out.size() > number){
			out = out.tail();
		}
		return out;
	}
	
	public static <T> LinkedList<T> reverse(LinkedList<T> in) {
	
		LinkedList<T> out = new LinkedList<T>();
		while(!in.isEmpty()) {
			out = out.add(in.head().getValue());
			in = in.tail();
		}
		return out;
	}
	
	/**
	 * Appends the <code>first</code> to the <code>second.</code> 
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> LinkedList<T> append(LinkedList<T> first, LinkedList<T> second) {

		if(first == null) return second;
		if(second == null) return first;
		
		LinkedList<T> reversedFirst = reverse(first);
		
		while(!reversedFirst.isEmpty()) {
			second = second.add(reversedFirst.head().getValue());
			reversedFirst = reversedFirst.tail();
		}
		
		return second;
	}
	
	/**
	 * TODO: move to LinkedList? 
	 * Like map, but without the accumulation.  Executes the supplied <code>function</code> on every element of the supplied <code>list</code>.
	 * @param function
	 * @param list
	 */
	public static <S,D> void foreach(Function<S,D> function, LinkedList<S> list) {
		
		/*
		 * Recursive procedure and process
		 */
		if(list.isEmpty()) {    // base case
			return;
		} else {
			function.eval(list.head().getValue());
			foreach(function, list.tail());
		}
		
		/*		
		 	// Iterative procedure and process
		 	while(!list.isEmpty()) {
				function.eval(list.head().getValue());
				list = list.tail();
			}	
			
		 
		// Iterative procedure and process 2
		Iterator<S> iterator = list.iterator();
		while (iterator.hasNext()) {
			S next = iterator.forward();
			function.eval(next);
		}
		*/
		
	}
	
	
	public static void display(LinkedList<?> in){
		
		while(!in.isEmpty()){
			System.out.println(in.head());
			in = in.tail();
		}
	}
	
	public static void display(BinarySearchTree<?> in){
		display(in.toList());
	}	
}
