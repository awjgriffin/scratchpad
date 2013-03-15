package selfstudy.fp.functions;

import selfstudy.fp.AbstractReduceable;
import selfstudy.fp.Function;
import selfstudy.fp.Reduceable;

public class NumericFunctions {

	public static Reduceable<Long,Long> multiplyLongs(){
		
		return new AbstractReduceable<Long,Long>(){
			public Long eval(Long d, Long s) {
				return d.longValue() * s.longValue();
			}
		};
	}
	
	public static Reduceable<Double,Double> multiplyDoubles(){
		
		return new AbstractReduceable<Double,Double>(){
			public Double eval(Double d, Double s) {
				return d.doubleValue() * s.doubleValue();
			}
		};
	}	
	
	public static AbstractReduceable<Long,Long> add(){
		
		return new AbstractReduceable<Long,Long>(){
			public Long eval(Long d, Long s) {
				return d.longValue() + s.longValue();
			}
		};
	}
	
	
	public static Function<Long,Long> square(){
		
		return new Function<Long, Long>() {

			public Long eval(Long source) {
				return source != null ? (source * source) : 0;
			}
		};
	}		
	
	
	//Moved to {@link AbstractReduceable} interface
/*	public static Function<Long, Long> curry(final Reduceable<Long, Long> f, final Long init) {
		
		return new Function<Long, Long>(){

			public Long eval(Long source) {
				return f.eval(source, init);
			}
		};
	}
	*/
	
	public static Function<Long,Long> addTen(){
		return add().curry(10L);
	}
	
}
