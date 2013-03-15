package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassUtils {

	
	public static boolean inheritsFrom(Class<?> cls, Class<?> interfaceClass) {
		
		return findInterfaces(cls).contains(interfaceClass);
		
	}
	
	private static List<Class<?>> findInterfaces(Class<?> cls) {
		
		if(cls.equals(Object.class)) {
			return new ArrayList<Class<?>>();  
		} else {
			
			List<Class<?>> interfaces = findInterfaces(cls.getSuperclass());
			
			Class<?>[] immediateInterfaces = cls.getInterfaces();
			
			for (int i = 0; i < immediateInterfaces.length; i++) {
				interfaces.add(immediateInterfaces[i]);
			}
			
			return interfaces;
		}
		
	}
	
	public static void listInterfaces(Class<?> cls) {
		
		Iterator<Class<?>> iterator = findInterfaces(cls).iterator();
		while (iterator.hasNext()) {
			Class<?> c = (Class<?>) iterator.next();
			System.out.println(c.getName());
		}
	}
	
}
