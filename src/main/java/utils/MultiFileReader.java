package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * A buffered reader which spans multiple files.
 * TODO
 */
public class MultiFileReader {

	private final Logger logger = Logger.getLogger(MultiFileReader.class);
	
	private FileReader[] fileReaders;
	
	private BufferedReader bufferedReader;
	
	private int counter = 0;
	
	public MultiFileReader(String...fileNames) {

		fileReaders = new FileReader[fileNames.length];
		
		for (int i = 0; i < fileNames.length; i++) {
			
			String fileName = fileNames[i];
			if(new File(fileName).exists()){
				try {
					fileReaders[i] = new FileReader(fileName);
				} catch (FileNotFoundException e) {
					logger.error( String.format( "Couldn't find file %s", fileName ), e );
				}
			}
		}
		
		reset();
	}


	/**
	 * Returns one line of text.<br/>
	 * Seamlessly moves onto the next file in the queue<br/> 
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		
		while(bufferedReader != null){
			
			String line = bufferedReader.readLine();
			if(line == null) {
				bufferedReader = counter < fileReaders.length ? new BufferedReader(fileReaders[++counter]) : null;
			} else return line; 
		}
		
		return null;
	}
	
	
	public void reset() {
		counter = 0;
		bufferedReader = fileReaders.length > 0 ? new BufferedReader( fileReaders[counter] ) : null;
	}
	
}
