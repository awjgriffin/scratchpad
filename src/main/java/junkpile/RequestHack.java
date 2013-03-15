package junkpile;
/*
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestHack {

	private static Log logger = LogFactory.getLog(RequestHack.class);
    
    public static final Object getUserPrincipal(HttpServletRequest request) {
    	
        HttpSession session = request.getSession();
        Class clazz = session.getClass();
        
        try
        {
            Field field = clazz.getDeclaredField("session");
            field.setAccessible(true);
            Object value = field.get(session);
            
            Class clazz2 = value.getClass();
//            Class[] paramClass = {Principal.class};
            Method method = clazz2.getMethod("getPrincipal", null );
            method.setAccessible(true);
            Object[] param = new Object[0];
            
            Object result = method.invoke(value, param);

            return result;
        }
        catch(Throwable e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    private static Principal getPrincipal(final String username) {
    	
        return new Principal() {

            private String principalName = username;

            public String getName() {
                return principalName;
            }
        };
    }
}*/