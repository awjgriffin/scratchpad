package misc;

public class Misc {

	
	public static void throwException() throws Exception {
		
		try {
			
			throw new Exception("Thrown in try");
			
		} catch(Exception e) {
			
			throw new Exception("Thrown in catch");
			
		} finally {
			
			throw new Exception("Thrown in finally");
		}
		
	}
	
	
	public static void main(String[] args) {
	
		try {
			throwException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
