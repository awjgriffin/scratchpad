package selfstudy.fp;

public interface Reduceable<D, S> {

	D eval(D d, S s);
	
	// should this be here, or only on some skeleton implementation?  
	// if you add this, dont' you have to add map, reduce, etc?
	// This implementation is inspired by the prototype.add(function()...) in JavaScript
//	Function<D, D> curry(final S init);   
}
