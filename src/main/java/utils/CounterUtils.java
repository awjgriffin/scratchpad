package utils;

import java.io.File;
import java.io.FileFilter;


public class CounterUtils {

	static int count = 0;
	
	public static int countFilesWithExtension(final String extension, String fromWhere) {
		
		File start = new File(fromWhere);
		
		if( !start.isDirectory() ){
			
			System.out.println("Please specify a valid directory as the second argument.");
			System.exit(0);
			
		} else {
			
			FileFilter fileFilter = new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {

					if(pathname.isDirectory()){
						return true;
					} else {
						
						if( pathname.getName().lastIndexOf(".") != -1 ){
							
							String fileExtension = pathname.getName().substring((pathname.getName().lastIndexOf(".") + 1), pathname.getName().length());
							return fileExtension.equalsIgnoreCase(extension);
						} else 
							{	return false;	}
					}
				}
			};

			recurseFiles(start, fileFilter);
			
			System.out.println(String.format("Count of files with %s extension: %d", extension, count));
			
		}
		
		return count;
	}
	
	
	private static void recurseFiles(File top, FileFilter filter) {
		
		File[] listFiles = top.listFiles(filter);
		
		for(int i = 0; i < listFiles.length; i++){
			
			File file = listFiles[i];
			if(file.isDirectory()){
				recurseFiles(file, filter);
			} else {
				System.out.println(file.getAbsolutePath());
				count++;
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		

//		countFilesWithExtension("java", "C:\\Users\\Public\\Documents\\workspace\\scratchpad\\src\\main\\java");
		
		countFilesWithExtension("java", "C:\\Users\\Public\\Documents\\workspace\\riskengine-eod-6.3.3.22-SNAPSHOT\\src");
	}
	
}
