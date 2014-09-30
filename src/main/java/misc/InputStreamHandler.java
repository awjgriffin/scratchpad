package misc;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamHandler extends Thread
{
    /**
     * Stream being read
     */
    
    private InputStream stream;
    
    /**
     * The StringBuffer holding the captured output
     */
    
    private StringBuffer captureBuffer;
    
    private static int handlerCounter=0; 
    
    /**
     * Constructor. 
     * 
     * @param 
     */
    
    public InputStreamHandler( StringBuffer captureBuffer, InputStream stream )
    {
        this.stream = stream;
        this.captureBuffer = captureBuffer;
        this.setName("ProcessStreamHandler"  + (++handlerCounter));
        start();
    }
    
    /**
     * Stream the data.
     */
    
    public void run()
    {
        try
        {
            int nextChar;
            while( (nextChar = this.stream.read()) != -1 )
            {
                this.captureBuffer.append((char)nextChar);
            }
        }
        catch( IOException e )
        {
            System.err.println( String.format("IOException caught whilst reading input stream: %s", e) );
        }
    }
}
