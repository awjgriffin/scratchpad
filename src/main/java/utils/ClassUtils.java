package utils;

import java.io.IOException;
import java.util.ArrayList;
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
		
		try {
			new PrintUtils().printIterator( findInterfaces(cls).iterator() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
