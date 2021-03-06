package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;

public class PrintUtils {

	private final Writer writer;

	public PrintUtils() {
		writer = new PrintWriter(System.out);
	}

	public PrintUtils(Writer writer) {
		super();
		this.writer = writer;
	}
	
	public void printIterator(Iterator<?> in) throws IOException {
		
		while(in.hasNext()) {
			
			try {

				writer.write( in.next().toString() );
				writer.write( "\n" );
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		writer.flush();
		
	}
	
}