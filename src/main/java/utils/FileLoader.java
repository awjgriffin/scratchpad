package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

public class FileLoader {

	
	public static interface Command<T> { 		public void execute(T in);  	};
	
	
	protected static File loadFile(String location) {
		
		// first try via resource.asStream
		// then via File  
		return new File( location );
	}
	

	protected static void readFile(File f, Command<String> c) throws IOException {
		
		BufferedReader buffer = new BufferedReader( new FileReader(f) );
		
		TreeSet<String> set = new TreeSet<String>();
		
		String line;
		while( (line = buffer.readLine()) != null ) {
			
			String[] elements = line.split("\\.");
			set.add( elements[0]  + "." + elements[elements.length-5] + "." + elements[elements.length-4] );
			
/*
			if (line.contains(".ANY.") || line.contains("_ANY_")) {
				set.add( line.split("\\.")[0] + "." + line.split("\\.")[1] + "." + line.split("\\.")[2]);				
			} else if( line.split("\\.").length == 6)  {
				set.add( line.split("\\.")[0] + "." + line.split("\\.")[2] + "." + line.split("\\.")[3]);
			} else {
				set.add( line.split("\\.")[0] + "." + line.split("\\.")[3] + "." + line.split("\\.")[4]);
			}
			*/
		}
		
		
		new PrintUtils().printIterator( set.iterator() );
		System.out.println( String.format("Unique dictionaries: %d", set.size() ));
		
	}
	
	
	
	public static void main(String[] args) throws IOException {

		
//		System.out.println("LAG_SWAPS_EXOTIC_XCCY_ALL_CMS.ANY.TESTBOOK.TOM_TESTBOOK.1.20140801.TESTBOOK".split("\\.").length);
		
		readFile( loadFile("C:\\Users\\grandre\\useful docs\\JIRA\\RTRE-47955 - Summary and Detail discrepancy (productivity)\\jobsMissingInTargetForJobId68.txt"), null);
		
	}
	
	
}
