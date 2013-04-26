package utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

public class Utils {
	
	public static String getWithDefault(Map<String, String> map, String key, String def){
		return (map == null || key == null || map.get(key) == null) ? def : map.get(key);
	}

	public static String getWithBlankDefault(Map<String, String> map, String key){
		return getWithDefault(map, key, "");
	}		
	
	public static String enTag(String in) {
		return new StringBuffer("<").append(in).append(">").toString();
	}
	
	
	public static <E> List<E> enumerationToList(Enumeration<E> e) {
		
		List<E> list = new ArrayList<E>();
		while(e.hasMoreElements()) {
			list.add(e.nextElement());
		}
		return list;
	}
	
	public static ThreadFactory daemonThreadFactory() {
	
		return new ThreadFactory(){
	
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
			
		};
	}
	
	public static <K,V> Map<K, V> disableCacheMap(CacheMap<K,V> in) { 		return in.turnOff(); 	}
	
	public static <E> List<E> disableCacheList(CacheList<E> in) { 		return in.turnOff();   	}
	
}
