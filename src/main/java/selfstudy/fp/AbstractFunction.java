package selfstudy.fp;

@Deprecated
public abstract class AbstractFunction<S, D> implements Function<S, D> {
	
	public Function<D, D> curry(final Reduceable<D, S> f, final S init) {
		
		return new Function<D, D>(){
			public D eval(D source) {
				return f.eval(source, init);
			}
		};
	}	
}
