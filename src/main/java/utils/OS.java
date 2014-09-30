package utils;

public enum OS {

	Windows, Unix, SunOS, Mac, Undefined;
	
	public static OS getCurrentOS() {
		
		String OS = System.getProperty("os.name").toLowerCase();
		 
		if (OS.indexOf("win") >= 0) return Windows;
		if (OS.indexOf("mac") >= 0) return Mac;
		if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) return Unix;
		if (OS.indexOf("sunos") >= 0) return SunOS;
		else return Undefined;
	
	}
}
