package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;

public class XMLUtils {

	final private static Logger logger = Logger.getLogger(XMLUtils.class);
	
	final private static Properties properties = new Properties();
	
	public static Properties getApplicationProperties() {
		
		if( properties.size() == 0){
			
			try {
				properties.load( XMLUtils.class.getResourceAsStream("/application.properties") );
			} catch (IOException e) {
				logger.error("Exception loading application properties: ", e);
			}
		}
		
		return properties;
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
	 * Sets the {@link Node.textContent} property with the given value.
	 * 
	 * @param xPathExpression
	 * @param startingNode
	 * @param value
	 */
	public static void setNodeString(String xPathExpression, Node startingNode, String value)  {
		
		Node node = findNode(xPathExpression, startingNode);
		
		if(node == null) {
			logger.warn( String.format("Invalid Node: %s", xPathExpression));
		} else {
			node.setTextContent(value);
		}
		return;
	}	
	
	/**
	 * Imports/clones a tree from one{@link Node} created in another document into a second.	<br/>
	 * Only use this if your {@link Document} implementation does not support {@link Document#importNode}.   <br/>
	 * NOTE: this is the simplest implementation possible; it doesn't consider any type of Node other than text <br/>
	 * SIDE EFFECTS: the {@link Node#ownerDocument} is modified.  Therefore the return object does not have to be re-assigned in order to realize the changes.<br/> 
	 * Credit for the tree traversal: http://myarch.com/treeiter/traditways
	 * 
	 * @param targetNode - the Node/document which will be modified. 
	 * @param sourceNode - the Node/document from which the changes are taken. 
	 * @return the ownerDocument {@link Document} object of the targetNode parameter.  This object is modified as a side-effect also.
	 * 
	 */
	public static Document addNodeFromOtherDocument(Node targetNode, Node sourceNode) {
		
		Document ownerDocument = targetNode.getOwnerDocument();
		Node node = sourceNode;
		Node newNode = null;
		Node tempTargetNode = targetNode;

		while( node != null ) {

			newNode = ownerDocument.createElement( node.getNodeName() );
			newNode.setTextContent( node.getTextContent() );

			if( node.hasAttributes() ) {
				
				NamedNodeMap attributes = node.getAttributes();
				for( int i = 0; i < attributes.getLength(); i++ ) {
					
					Node item = attributes.item( i );
					Attr attribute = ownerDocument.createAttribute( item.getNodeName() );
					attribute.setNodeValue( item.getNodeValue() );
					newNode.getAttributes().setNamedItem( attribute );
				}
			}
			
			tempTargetNode.appendChild( newNode );			
			tempTargetNode = newNode;
			
			Node childNode = getFirstElementChildNode( NodeListToList( node.getChildNodes() ) );
			if( childNode  != null  ) { 
				node = childNode;
			} else {

				while ( getNextElementSibling( node ) == null && node != sourceNode) {	 node = node.getParentNode(); tempTargetNode = tempTargetNode.getParentNode();	}
		
				node = getNextElementSibling( node );
				tempTargetNode = tempTargetNode.getParentNode();	// get parent because there is no appendSibling() method, so in order to create a sibling, I have to go to the parent and use appendChild()...
			}			
			
		} 

		return ownerDocument;
	}

	/**
	 * @param in
	 * @return The first sibling of type {@link Node.ELEMENT_NODE} if one exists, null otherwise.
	 */
	public static Node getNextElementSibling(Node in) {
		
		Node elementSibling = in.getNextSibling();

		while ( elementSibling!= null ) {  
			if(elementSibling.getNodeType() == Node.ELEMENT_NODE) { return elementSibling; }  else { elementSibling = elementSibling.getNextSibling(); }		
		}
		
		return elementSibling;
	}
	
	
	/**
	 * @param nodeList
	 * @return The first child node of type {@link Node.ELEMENT_NODE} if one exists, null otherwise.
	 */
	public static Node getFirstElementChildNode(List<Node> nodeList) {
		
		Node elementNode = null;
		
		for(Node n : nodeList) { if( n.getNodeType() == Node.ELEMENT_NODE ) {   return n;   } 		}
		
		return elementNode;
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
				
				public boolean hasNext() {
					return index < nodeList.getLength();
				}

				public Node next() {
					return nodeList.item( index++ );
				}

				public void remove() {  				}

				
			});
			
		}
		
	} 
	
	public static Document parseXMLString(String xml) throws SAXException, IOException, ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( new InputSource( new StringReader( xml ) ) );
	}
	
	public static Document parseXMLInput(InputStream xml) throws SAXException, IOException, ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( xml );
	}	

	public static String transformXMLToString(Document xmlDocument, String xslLocation) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {

		DOMSource source = new DOMSource( xmlDocument );
		Transformer transformer = 
				TransformerFactory.newInstance()
					.newTransformer( new StreamSource( XMLUtils.class.getResourceAsStream( xslLocation ) ) );
		
		StringWriter stringWriter = new StringWriter();
		
		StreamResult result = new StreamResult( stringWriter );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		transformer.transform( source, new StreamResult( baos ) );		
		
		
		transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");		
		transformer.transform( source, result );
		
		return stringWriter.toString();
	}	
	
	public static void transformDebug(Document xmlDocument, String xslLocation) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {

		DOMSource source = new DOMSource( xmlDocument );
		Transformer transformer = 
				TransformerFactory.newInstance()
					.newTransformer( new StreamSource( XMLUtils.class.getResourceAsStream( xslLocation ) ) );
		
		StreamResult consoleResult = new StreamResult( System.out );   
		transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform( source, consoleResult ); 
	}		
	
	
	public static <T extends Exception> void errorIf( boolean doIf, T exception) throws T {
		
		if( doIf ){
			logger.error( exception.getMessage(), exception );
			@SuppressWarnings("unchecked")
			T ex = (T) new Exception( exception );
			throw ex;
		}
	} 		
	
	
	public interface Condition {
		boolean doIf();
	}

	public static <T extends Exception>  void errorIf(Condition condition, T exception) throws T {
		errorIf( condition.doIf(), exception);
	}
	
}
