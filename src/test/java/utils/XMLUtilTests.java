package utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Ignore;

import selfstudy.fp.ReaderIterable;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import static utils.XMLUtils.*;

public class XMLUtilTests {

	@Ignore(value="This was a home-made parsing attempt before I discovered how to use Nexus REST API")
	public void testParseHTMLtoXML() throws Exception {
		
    	URL url = new URL( "http://gmrepo.gslb.db.com:8481/nexus-webapp/content/repositories/riskenginesnapshots/com/db/gm/riskengine/dbax-results-parser/bucketgamma-0.01-SNAPSHOT/ ");
		URLConnection conn = url.openConnection();
		conn.setReadTimeout( 5000 );   

		BufferedReader br = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
		
		StringWriter sw = new StringWriter();
		sw.append("<root>");
		
		for( String s : Iterables.filter( new ReaderIterable(br) , new Predicate<String>(){

			public boolean apply(String input) { 
			
				//TODO: convert to REGEX and skip the XSL transform
				return input.contains("http://gmrepo.gslb.db.com:8481/nexus-webapp/content/repositories/riskenginesnapshots");
			} } ) ){
			sw.append( s );
		}
		sw.append("</root>");
		
		System.out.println( sw.toString() );
		
		transformDebug( parseXMLString( sw.toString() ), "/xml/debug-html-to-xml.xsl");
		
	}
	
}
