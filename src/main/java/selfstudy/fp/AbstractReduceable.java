package selfstudy.fp;

/**
 * Provides some useful methods which may be used behind the scenes but may not be wanted on {@link Reduceable} itself.
 * @author grandre
 *
 * @param <D>
 * @param <S>
 */
public abstract class AbstractReduceable<D,S> implements Reduceable<D, S> {
	
	public Function<D, D> curry(final S init) {
		
		return new Function<D, D>(){
		
			final AbstractReduceable<D, S> thisReduceable = AbstractReduceable.this;

			public D eval(D source) {
				return thisReduceable.eval(source, init);
			}
		};
	}	
}
