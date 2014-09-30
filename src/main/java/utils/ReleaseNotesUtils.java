package utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Lists;

/**
 * A collection of miscellaneous utilities to fill in a few gaps. 
 * 
 * @author grandre
 *
 */
public class ReleaseNotesUtils {
	
	private final static Logger logger = Logger.getLogger( ReleaseNotesUtils.class );
	
	public static boolean cantParseAsNumber(String in) {

		if( !StringUtils.isEmpty( in ) ) {
			
			try {
				new Double(in);
				return false;
			} catch(NumberFormatException e) { /* ignore */ }
		} 
		
		return true;
	}
	
	/**
	 * Creates a {@link String} by combining the elements in the given array, separated by the given delimiter.
	 * @param arrayIn
	 * @param delim
	 * @return
	 */
	public static String join(String delim, String...arrayIn ) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < arrayIn.length; i++) {
			sb.append(arrayIn[i]).append( i < arrayIn.length -1 ? delim : "");
		}
		
		return sb.toString();
	}	
	
	/**
	 * @param in
	 * @return An array containing the elements of the source string, tokenized by a delimiter (a comma).
	 */
	public static String[] delimitedStringToArray(String in) {
		return delimitedStringToArray(in, ",");
	} 

	/**
	 * @param in
	 * @return An array containing the elements of the source string, tokenized by a delimiter (supplied).
	 */	
	public static String[] delimitedStringToArray(String in, String delimiter) {
		return StringUtils.isEmpty(in) ? new String[]{} : in.split(delimiter); 
	} 	
	
	/**
	 * @param in
	 * @return Returns the supplied String encoded in Base64 format.
	 */
	public static String encodeBase64( String in ) {
		return new String( Base64.encodeBase64( in.getBytes() ) );
	}

	/**
	 * @return Returns a decoded version of the supplied Base64-encoded String decoded.
	 */	
	public static String decodeBase64( String base64EncodedString ) {
		return new String( Base64.decodeBase64( base64EncodedString.getBytes() ) );
	}	
	
	/**
	 * Finds a node starting at the given {@link Node}, or null if it can't be found.  
	 * You can pass in the Document or one of its child nodes.
	 * @param xPathExpression
	 * @param startingNode - the node to start at.
	 * @return
	 */
	public static Node findNode(String xPathExpression, Node startingNode)  {
				
		try {
			return (Node) XPathFactory.newInstance().newXPath().evaluate( xPathExpression, startingNode, XPathConstants.NODE );
		} catch (XPathExpressionException e) {
			return null;
		}		
	}

	/**
	 * Finds a text value starting at the given {@link Node}, or an empty string if it can't be found.  
	 * You can pass in the Document or one of its child nodes.
	 * @param xPathExpression
	 * @param startingNode - the node to start at.
	 * @return
	 */	
	public static String findTextValueInNode(String xPathExpression, Node startingNode)  {
		
		try {
			return XPathFactory.newInstance().newXPath().evaluate( xPathExpression, startingNode );
		} catch (XPathExpressionException e) {
			return "";
		}		
	}	
	
	/**
	 * Finds a node list starting at the given {@link Node}, or null if it can't be found.  
	 * You can pass in the Document or one of its child nodes.
	 * @param xPathExpression
	 * @param startingNode - the node to start at.
	 * @return
	 */
	public static NodeList findNodeList(String xPathExpression, Node startingNode)  {
				
		try {
			return (NodeList) XPathFactory.newInstance().newXPath().evaluate( xPathExpression, startingNode, XPathConstants.NODESET );
		} catch (XPathExpressionException e) {
			return null;
		}		
	}	
	
	/**
	 * A recursive file search for the exact filename supplied, starting at the directory supplied.</br> 
	 * Case insensitive.
	 * @param directoryToStart the directory to begin the search in.
	 * @param filename the name of the file to search for.
	 * @return a {@link File} handle if found, <code>null</code> otherwise.
	 */
	public static File findFile(File directoryToStart, String filename, Set<String> dirsToIgnore) {
		
		for (File file : directoryToStart.listFiles()) {
			
			if( file.isDirectory() ) {
				
				if(!dirsToIgnore.contains( file.getName() ) ) { 
					
					File findFile = findFile(file, filename, dirsToIgnore);
					
					if( findFile != null ) return findFile; 
				}
				
			} else if( file.getName().equalsIgnoreCase( filename ) )  return file;
		}		
		
		return null; // not found
	}	
	
	
	/**
	 * Attempts to decrement the Maven version based on artifact and version.  
	 * @param artifact
	 * @param ver
	 * @return The decremented version, or null if it can't be parsed.
	 */
	public static String decrementMavenVersion(String artifact, String ver, String defaultName) {
		
		String artifactAndVersion = String.format("%s-%s", artifact, ver);
		
		Pattern [] patterns = {
				Pattern.compile("[\\d|\\.]+$"), // right-most numbers 
				Pattern.compile("(^[\\.|\\d]+[^-|\\D]+?)") // start of version string, followed by optional alpha characters 
				}; 

		for(Pattern pattern : patterns) {
			
			Matcher matcher = pattern.matcher( ver );  
			
			if( matcher.find()) {
				
				String version = ver.substring( matcher.start(), matcher.end() );
				logger.debug(String.format("Decrementing version: %s", version));
				
				String[] versionElements = version.split("\\.");
				
				for (int i = (versionElements.length - 1); i >= 0; i--) {
					
					String thisElement = versionElements[i];
					
					if( Pattern.matches("0+", thisElement ) ) {
						versionElements[i] = versionElements[i].replaceAll("0", "9");
					} else {
						versionElements[i] = new BigDecimal( thisElement ).subtract( new BigDecimal(1) ).toString();
						break;
					}
						
				}
				
				logger.debug( String.format( "Decremented version to: %s", join( ".", versionElements  ) ) );
				
				return artifactAndVersion.replaceAll("-SNAPSHOT", "").replaceAll(version, join( ".", versionElements  )); //TODO: replaceAll is too clumsy, need to replace lastInstanceOf
				
			}
			
		}
		
		return defaultName;
	}	
	
	/**
	 * @param nodeList
	 * @return A more usable {@link java.util.List} from the XML NodeList.
	 */
	public static List<Node> NodeListToList( final NodeList nodeList ) {
		
		if( nodeList == null || nodeList.getLength() == 0) {
			return new ArrayList<Node>();
		} else {

			return Lists.newArrayList( new Iterator<Node>() {

				int index = 0;
				
				@Override
				public boolean hasNext() {
					return index < nodeList.getLength();
				}

				@Override
				public Node next() {
					return nodeList.item( index++ );
				}

				@Override
				public void remove() {   				}
				
			});
			
		}
		
	} 

	/**
	 * Also works as &quot;rename file &quot;.
	 * @param from
	 * @param to
	 */
	public static boolean moveFile(File from, File to) {

		boolean wasThere = from.exists();
		
		// credit for below: http://stackoverflow.com/questions/1000183/reliable-file-renameto-alternative-on-windows
		for (int i = 0; i < 20; i++) {
		    if (from.renameTo(to))
		        break;
		    System.gc();
		    Thread.yield();
		}
		
		return wasThere && !from.exists() && to.exists();
		
	}
	
}
