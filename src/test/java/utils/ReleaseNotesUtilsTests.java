package utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;


import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReleaseNotesUtilsTests {

	
	@Test
	public void testGetReleases() throws SAXException, IOException, ParserConfigurationException {
		
		File changesXMLFile = ReleaseNotesUtils.findFile(new File("C:\\Users\\grandre\\ws\\eod-agg-TRUNK\\src\\changes\\"), "changes.xml", new HashSet<String>());
		
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(changesXMLFile);
		
		NodeList nodeList = ReleaseNotesUtils.findNodeList( "//release", document );
		
		Assert.assertNotNull(nodeList);
		
		List<Node> nodeListToList = ReleaseNotesUtils.NodeListToList( nodeList );
		
		for(Node node : nodeListToList) {
			
			System.out.println( ReleaseNotesUtils.findTextValueInNode("@version", node) );
		}
		
	}
	
}
