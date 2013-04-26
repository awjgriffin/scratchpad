package utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author grandre
 *
 * @param <K>
 * @param <V>
 */
public class DefaultedMap<K, V> extends AbstractMap<K, V> {
	
	private Map<K,V> base = null;
	
	private DefaultedMap (Map<K,V> base){
		this.base = base;
	}

	/**
	 * Map factory method.
	 * @param base - the source Map.
	 * @return a wrapped instance of the source Map.
	 */
	public static <K,V> DefaultedMap<K,V> createMap(Map<K,V> base){
		
		return new DefaultedMap<K,V>(base);	
	}	
	
	
	/**
	 * TODO: what was the intention of this?
	 * @return
	 */
	public DefaultedMap<K,V> getLoggingProxy(){
		
		return (DefaultedMap<K,V>) Proxy.newProxyInstance(DefaultedMap.class.getClassLoader(), new Class[]{Map.class}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				
				System.out.println("The method name is: " + method.getName());
				
				return method.invoke(base, args);
			}
		});
		
	}		
	
	/**
	 * @param key - 
	 * @param defaultValue - A default value which is returned if the value is not found.
	 * @return either the value corresponding to the key, or the default supplied.
	 */
	public V get(K key, V defaultValue){
		return (key == null || base.get(key) == null) ? defaultValue : base.get(key);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return base.entrySet();
	}	
	
}
