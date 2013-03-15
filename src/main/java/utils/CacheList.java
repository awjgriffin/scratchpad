package utils;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * A {@link java.util.AbstractList} implementation which allows the user to specify a reload method and interval.
 * Once instantiated, this list will refresh its own data using the supplied method, at the interval given.
 * There is a protected method {@link #turnOff} to disable the caching features.
 * 
 * @author grandre
 *
 * @param <E>
 */
@SuppressWarnings("unused")
public class CacheList<E> extends AbstractList<E> implements List<E> {

	private final static Logger logger = Logger.getLogger(CacheList.class);
	
	public interface ReloadMethod<E> {
		List<E> reload() throws Exception;
	}
	
	final private ReloadMethod<E> loadMethod;
	final private EvictionPeriod evictionPeriod;
	final private List<E> cache;
	final private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Utils.daemonThreadFactory());
	
	public CacheList(final ReloadMethod<E> loadMethod, final EvictionPeriod evicitionPeriod) {
		
		this.loadMethod = loadMethod;
		this.evictionPeriod = evicitionPeriod;
		
		cache = Collections.synchronizedList( new ArrayList<E>() );
		
		scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				
				List<E> newValues;
				try {
					newValues = loadMethod.reload();
				} catch (Exception e) {
					logger.error("SEVERE: Could not reload cache; data may be stale or empty.");
					return; 
				}
				
				synchronized(cache) {
					cache.clear();
					cache.addAll(newValues);
				}
			}
			
		}, 0l, evicitionPeriod.getAmt(), evicitionPeriod.getUnit());
	}

	@Override
	public E get(int index) {
		return cache.get(index);
	}

	@Override
	public int size() {
		return cache.size();
	}
	
	@Override
	public void clear() {
		cache.clear();
	}
	
	protected List<E> turnOff() {
		
		scheduledThreadPoolExecutor.shutdownNow();
		
		List<E> plainOldList = new ArrayList<E>();
		plainOldList.addAll(cache);
		
		return plainOldList;
	}
}
