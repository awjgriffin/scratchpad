package misc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

public class Misc {

	
	public static void throwException() throws Exception {
		
		try {
			
			throw new RuntimeException("Thrown in try");
			
		} catch(Exception e) {
			
			throw new Exception("Thrown in catch");
			
		} finally {
			
			throw new Exception("Thrown in finally");
		}
		
	}
	
	
	public static void regexTest() {
		

		System.out.println( String.format("Today's date is: %s", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())) );
		
		String str = "RTRE-43555:One issue\n- plus more stuff about the RTRE43555 jira\n- oh, and another thought\nGEDEOI-099 Another issue\n- and this is regarding GEDEOI-097";

/*		Matcher matcher = Pattern.compile("(^\\w+-\\d+)", Pattern.MULTILINE).matcher(str);
		matcher.useAnchoringBounds(false);
		while(matcher.find()){
		
			System.out.println( matcher.group(2) );
		}*/
		
		String[] split = str.split("\\w+-\\d+:?");
		
		for(int i = 0; i < split.length; i++) {
		
			String splitElement = split[i];
			if(StringUtils.isEmpty( splitElement ) ) { continue;}
			System.out.println(splitElement);
			
		}
		
		String jiraWithType = "RTRE-43555:One issue\n- plus more stuff about the RTRE43555";
		
		Matcher matcher = Pattern.compile( "^\\w+-\\d+:?(.*):" ).matcher( jiraWithType );
		
		System.out.println( matcher.find() ? String.format( "Found %s", matcher.group(1) ) : "no match found.");
	
//		String artifactAndVersion = "intraday-engine-2.2.1.0-RATES";
		String artifactAndVersion = "intraday-engine-2.1.000.00-RATES-9.88";
		
		matcher = Pattern.compile("[\\d|\\.]+").matcher( artifactAndVersion );
		
		if(matcher.find()) {

			String version = artifactAndVersion.substring( matcher.start(), matcher.end() );
			System.out.println("Found version: " + version);
			String[] versionElements = version.split("\\.");

			for (int i = (versionElements.length - 1); i > 0; i--) {
				
				String thisElement = versionElements[i];
				
				if( Pattern.matches("0+", thisElement ) ) {
					versionElements[i] = versionElements[i].replaceAll("0", "9");
				} else {
					versionElements[i] = padWithZeroes( new BigDecimal( thisElement ).subtract( new BigDecimal(1) ).toString() );
					break;
				}
					
			}
			
			System.out.println("Decremented to: " + join(versionElements, "."));					
		}
		
		
		List<String> list = new ArrayList<String>();
		list.add("V5_BEFORE_MERGE_TO_HEAD");
		list.add("IntradayEngine-1_00");
		list.add("intraday-engine-config-2.1.0.21.3");
		list.add("intraday-engine-2.2.1.0-RATES");
		list.add("exp-2.2-JSM-Integration-before");
		list.add("BEFORE_EQUITIES_5_MERGE");
		list.add("2.1.0.9");
		list.add("2.1.0.0-before-relauncher-revert");		
		
		String regex = "[\\d|\\.\\_]+$";
		Pattern pattern = Pattern.compile(regex);
		System.out.println("Matches found:");
		for(String s : list) {
			
			Matcher m = pattern.matcher(s);
			if(m.find()) {
				System.out.println(m.group());
			}
			
		}
		
		
	}
	
	//TODO: fix this, it doesn't work
	public static String padWithZeroes(String in) {
		
		if( numberIsBad(in) ){  return "";	}
		
		String format = new Integer( 10 * in.length() ).toString().substring( Math.min( 1, in.length() ) );
		
		return new DecimalFormat(format).format( new Double(in) ); 
	}
	
	private static boolean numberIsBad(String in) {

		if(!StringUtils.isEmpty(in)) {
			
			try {
				new Double(in);
				return false;
			} catch(NumberFormatException e) {
				return true;
			}
			
		} else return true;
		
	}


	public static String join(String[] arrayIn, String delim) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < arrayIn.length; i++) {
			sb.append(arrayIn[i]).append( i < arrayIn.length -1 ? delim : "");
		}
		
		return sb.toString();
	}
	
	
	public static String encodeBase64(String in) {
		return new String( Base64.encodeBase64( in.getBytes() ) );
	}
	
	public static String decodeBase64(String base64EncodedString ) {
		return new String( Base64.decodeBase64( base64EncodedString.getBytes() ) );
	}
	
	
	private static void writeXML()
			throws TransformerConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		
	    String formattedOutput = "";
	    String xmlDocument = "C:\\Users\\Public\\Documents\\workspace\\intraday_tesla_branch\\target\\releaseNotesPlugIn\\tmp\\src\\changes\\changes.xml";
	    String xslDocument = "C:\\Users\\grandre\\useful docs\\dev\\releasenotes\\src\\main\\resources\\sort.xsl";
	    
	    try {

	    	Document document = 
	    				DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( new File( xmlDocument ) );
	    	
			Transformer transformer = 
	        		TransformerFactory.newInstance().newTransformer( new StreamSource(  new File(xslDocument) ) );

	        DOMSource source = new DOMSource( document );
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");		
	        transformer.transform( source, new StreamResult( baos ) );

	        formattedOutput = baos.toString();
	    	
/*	        
 * 			OLDE
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(document);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			// StreamResult result = new StreamResult(System.out);   // output to console for testing
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");		
//			transformer.transform(source, result);	        
			transformer.transform( source, new StreamResult( baos ) );
			 formattedOutput = baos.toString();
*/
	    } catch( Exception e ) {
	        e.printStackTrace();
	    }

	    System.out.println(formattedOutput);
		
	}	
	
	
	public static void specialNode() {
		
		Document document = null;
		
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		List<String> list = new ArrayList<String>();
		list.add("V5_BEFORE_MERGE_TO_HEAD");
		list.add("IntradayEngine-1_00");
		list.add("intraday-engine-config-2.1.0.21.3");
		list.add("intraday-engine-2.2.1.0-RATES");
		list.add("exp-2.2-JSM-Integration-before");
		list.add("BEFORE_EQUITIES_5_MERGE");
		list.add("2.1.0.9");
		list.add("2.1.0.0-before-relauncher-revert");
		
		
		for (String s : list) {

			Element releaseElement = document.createElement("release");
			
			releaseElement.setAttribute( "version", s );
			document.appendChild(releaseElement);
		}
		
	}
	
	public static Node getSpecialNode(final Element in) {
		
		return new Node() {
			
			@Override
			public String getNodeName() { return in.getNodeName();  }

			@Override
			public String getNodeValue() throws DOMException {		return in.getNodeValue(); }

			@Override
			public void setNodeValue(String nodeValue) throws DOMException { in.setNodeValue(nodeValue);			}

			@Override
			public short getNodeType() { return in.getNodeType(); }

			@Override
			public Node getParentNode() { return in.getParentNode();			}

			@Override
			public NodeList getChildNodes() {	return in.getChildNodes();			}

			@Override
			public Node getFirstChild() {	return in.getFirstChild();			}

			@Override
			public Node getLastChild() {	return in.getLastChild();			}

			@Override
			public Node getPreviousSibling() {	return in.getPreviousSibling();			}

			@Override
			public Node getNextSibling() {	return in.getNextSibling();			}

			@Override
			public NamedNodeMap getAttributes() {	return in.getAttributes();			}

			@Override
			public Document getOwnerDocument() {	return in.getOwnerDocument();			}

			@Override
			public Node insertBefore(Node newChild, Node refChild)
					throws DOMException {	return in.insertBefore(newChild, refChild);			}

			@Override
			public Node replaceChild(Node newChild, Node oldChild)
					throws DOMException {	return in.replaceChild(newChild, oldChild);			}

			@Override
			public Node removeChild(Node oldChild) throws DOMException {	return in.removeChild(oldChild);			}

			@Override
			public Node appendChild(Node newChild) throws DOMException {	return in.appendChild(newChild);			}

			@Override
			public boolean hasChildNodes() {	return in.hasChildNodes();			}

			@Override
			public Node cloneNode(boolean deep) {	return in.cloneNode(deep);			}

			@Override
			public void normalize() {	in.normalize();			}

			@Override
			public boolean isSupported(String feature, String version) {	return isSupported(feature, version);}

			@Override
			public String getNamespaceURI() {	return in.getNamespaceURI();	}

			@Override
			public String getPrefix() {		return in.getPrefix();	}

			@Override
			public void setPrefix(String prefix) throws DOMException {	in.setPrefix(prefix);			}

			@Override
			public String getLocalName() {	return in.getLocalName();			}

			@Override
			public boolean hasAttributes() {	return in.hasAttributes();			}

			@Override
			public String getBaseURI() {		return in.getBaseURI();	}

			@Override
			public short compareDocumentPosition(Node other)
					throws DOMException {	return in.compareDocumentPosition(other);			}

			@Override
			public String getTextContent() throws DOMException {	return in.getTextContent();			}

			@Override
			public void setTextContent(String textContent) throws DOMException {	in.setTextContent(textContent);	}

			@Override
			public boolean isSameNode(Node other) {	return in.isSameNode(other);	}

			@Override
			public String lookupPrefix(String namespaceURI) {	return in.lookupPrefix(namespaceURI);	}

			@Override
			public boolean isDefaultNamespace(String namespaceURI) {	return in.isDefaultNamespace(namespaceURI);	}

			@Override
			public String lookupNamespaceURI(String prefix) {	return in.lookupNamespaceURI(prefix);	}

			@Override
			public boolean isEqualNode(Node arg) {	return in.isEqualNode(arg);	}

			@Override
			public Object getFeature(String feature, String version) {	return in.getFeature(feature, version);	}

			@Override
			public Object setUserData(String key, Object data,
					UserDataHandler handler) {	return in.setUserData(key, data, handler);			}

			@Override
			public Object getUserData(String key) {	return in.getUserData(key);	}

			@Override
			public int hashCode() {
				// TODO 
				return super.hashCode();
			}

			@Override
			public boolean equals(Object obj) {
				// TODO 
				return super.equals(obj);
			}
			
			
		};
		
	}

	public static void main(String[] args) throws Exception {
	
		double d = .000001d;
		System.out.println(Float.MIN_VALUE );
		
		String test = "PROD prd prod_asia PRD_Ny";
		
		int i = 0;
		Matcher matcher = Pattern.compile("pro?d", Pattern.CASE_INSENSITIVE).matcher(test);
		while(matcher.find()){
			i++;
		};
		
		System.out.println(String.format("Found %d instances",i));
		
		System.out.println( Pattern.compile("[a-zA-Z]").matcher( "1234" ).find() ? "found letter" : "didn't find letter" );
		
		regexTest();
		
/*		try {
			throw new Exception();
		}
		finally {
			System.out.println("Print this before you exit");
		}*/
		
//		System.out.println( "20131111_1234g".matches("^20131111_\\d\\d\\d\\d[t|g]") ); 
		
//		System.getProperties().list(System.out);
		
/*		String str = "Secret";
		System.out.println( String.format("%s becomes %s which becomes %s", str, encodeBase64(str), decodeBase64(encodeBase64(str)) ) );
		*/
//		writeXML();
		
//		throwException();
		
	}
	
}
