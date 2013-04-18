package utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * 
 * @author grandre
 *
 * @param <K>
 * @param <V>
 */
public class RangeMap<K extends Long, V> implements Map<Long, V>{
	
	private final Map<Long, V> map;
	
	public RangeMap() {
		super();
		map = new TreeMap<Long, V>();  
	}

	private RangeMap(Map<Long, V> map) {
		super();
		this.map = map;
	}

	
	public RangeMap<Long, V> getBetween(Long min, Long max) {

		// TODO get map, return new RangeMap(new map);
		final Map<Long, V> clone = new TreeMap<Long, V>();
		
		for(Long l : this.keySet()) {
			
		}
		
		return null;
	}

	
	@Override
	public V put(Long key, V value) {
		// TODO 
		return null;
	}

	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO 
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO 
		return false;
	}

	@Override
	public V get(Object key) {
		// TODO 
		return null;
	}


	@Override
	public V remove(Object key) {
		// TODO 
		return null;
	}

	@Override
	public void putAll(Map<? extends Long, ? extends V> m) {
		// TODO 
		
	}

	@Override
	public void clear() {
		// TODO 
		
	}

	@Override
	public Set<Long> keySet() {
		// TODO 
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO 
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<Long, V>> entrySet() {
		// TODO 
		return null;
	}
	
	
	
	

}
 