package utils;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * A {@link java.util.AbstractMap} implementation which allows the user to specify a reload method and interval.
 * Once instantiated, this list will refresh its own data using the supplied method, at the interval given.
 * There is a protected method {@link #turnOff} to disable the caching features.
 * 
 * @author grandre
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("unused")
public class CacheMap<K, V> extends AbstractMap<K, V> implements Map<K,V> {

	private final static Logger logger = Logger.getLogger(CacheMap.class);
	
	public interface ReloadMethod<K,V> {
		Map<K,V> reload() throws Exception;
	}
	
	final private ReloadMethod<K,V> loadMethod;
	final private EvictionPeriod evicitionPeriod;
	final private Map<K, V> cache;
	final private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Utils.daemonThreadFactory());
	
	public CacheMap(final ReloadMethod<K,V> loadMethod, final EvictionPeriod evicitionPeriod) {
		
		this.loadMethod = loadMethod;
		this.evicitionPeriod = evicitionPeriod;
		
		cache = Collections.synchronizedMap( new HashMap<K,V>() );
		
		scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				
				Map<K, V> newValues;
				try {
					newValues = loadMethod.reload();
				} catch (Exception e) {
					logger.error("SEVERE: Could not reload cache; data may be stale or empty.");
					return; 
				}
				
				synchronized(cache) {
					cache.clear();
					cache.putAll(newValues);
				}
			}
			
		}, 0l, evicitionPeriod.getAmt(), evicitionPeriod.getUnit());
	}


	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return cache.entrySet();
	}

	@Override
	public void clear() {
		cache.clear();
	}

	protected Map<K, V> turnOff() {
		
		scheduledThreadPoolExecutor.shutdownNow();
		
		Map<K,V> plainOldMap = new HashMap<K,V>();
		plainOldMap.putAll(cache);
		
		return plainOldMap;
	}
	
}
