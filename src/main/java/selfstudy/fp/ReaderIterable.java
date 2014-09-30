package selfstudy.fp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public class ReaderIterable implements Iterable<String> {

	private final BufferedReader reader;
	
	public ReaderIterable( BufferedReader reader ) {
		super();
		this.reader = reader;
	}

	public Iterator<String> iterator() {

		return new Iterator<String>(){

			public boolean hasNext() {

				try {
					return reader.ready();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}

			public String next() { 
				
				try {
					return hasNext() ? reader.readLine() : null;
				} catch (IOException e) {
					return null;
				}
			}

			public void remove() {  /* unsupported*/ }
			
		};
		
	}
}
