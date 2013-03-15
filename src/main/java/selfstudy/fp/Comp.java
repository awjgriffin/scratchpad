package selfstudy.fp;

/**
 * Comp - composable function 
 * assuming list/array of functions, e.g. F,G,H,I,J,
 * apply functions J(I(H(G(F(source)))))....
 * @author grandre
 * TODO - functions only take one argument, but they should take two
 * @param <S>
 * @param <D>
 */
public class Comp<D> implements Function<D,D> {

	protected Function<D,D> functions [];
	
	public Comp(Function<D,D>... functions) {
		super();
		this.functions = functions;
	}

	public D eval(D source) {
		
		D result = source;
		
		for (int i = 0; i < functions.length; i++) {
			result = functions[i].eval(result);
		}
		
		return result;
	}
	
}
