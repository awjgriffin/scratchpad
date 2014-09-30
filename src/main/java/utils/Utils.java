package utils;

import java.io.File;
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
	
	
	/**
	 * Also works as &quot;rename file &quot;.
	 * @param from
	 * @param to
	 */
	public static boolean moveFile(File from, File to) {

		boolean wasThere = from.exists();
		
		// credit for below: http://stackoverflow.com/questions/1000183/reliable-file-renameto-alternative-on-windows
		for (int i = 0; i < 20; i++) {
		    if (from.renameTo(to))
		        break;
		    System.gc();
		    Thread.yield();
		}
		
		return wasThere && !from.exists() && to.exists();
		
/*		try{
			
			boolean wasThere = from.exists();
			
			switch( OS.getCurrentOS() ) {
			
				case Windows:
					Runtime.getRuntime().exec( String.format( "cmd /c move /y %s %s", from.getAbsoluteFile(), to.getAbsoluteFile() ) );
					break;
				case Unix:
				case SunOS:
					Runtime.getRuntime().exec( String.format( "mv %s %s", from.getAbsoluteFile(), to.getAbsoluteFile() ) );
					break;
				case Mac:
				default:
					return false;
			}
			
			Thread.sleep( 200 );
			
			boolean isGone = !from.exists();
			
			return wasThere && isGone;
			
		} catch (Exception e) {
	
			return false;
		} 		
		*/
		

		
	}
}
