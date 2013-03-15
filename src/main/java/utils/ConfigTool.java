package utils;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Takes one mandatory argument, a filename.
 * Takes one optional argument, a command.
 * 
 * maybe this can open a repl, with commands like:
 * logdir --> retrieve log dir
 * grimis --> see lonweb db connection info
 * lonweb --> see grimis db connection info
 * ports --> "ac: 1234, rmi: 4321" ,e.g.
 *
 * command-line usich:
 * java -cp scratchpad-0.0.1-SNAPSHOT-jar-with-dependencies.jar utils.ConfigTool %*
 * 
 * maybe load with ONE config file, and in the repl, have a command such as:
 * compare PC_XXX --> gives report
 * 
 * @author grandre
 *
 */
public class ConfigTool {

	private static final String BAD_XML_MSG = "Please provide a valid XML config filename as the first parameter.";
	private static final String INSUFFICIENT_PARAMS_MSG = "This command takes at least one parameter";

	private static final Logger logger = Logger.getLogger(ConfigTool.class); 
	
	private final Document baselineXML;
	private Document compareeXML;
	
	private Set<String> commandHistory = new HashSet<String>(){

		@Override
		public boolean add(String str) {

			if(!StringUtils.isEmpty(str)) { super.add(str);  }
			
			return true;
		}
		
		@Override
		public String toString() {

			int i = 1;
			StringBuffer stringBuffer = new StringBuffer().append("");
			
			for(String s : this){
				stringBuffer.append(i++).append(". ").append(s).append("\n");
			}
			return stringBuffer.toString();
		}
		
	};
	
	
	@SuppressWarnings("serial")
	private final static Set<String> differences = new HashSet<String>(){

		@Override
		public String toString() {

			StringBuffer stringBuffer = new StringBuffer().append("Differences:");
			
			for(String s : this){
				stringBuffer.append(s).append("\n");
			}
			return stringBuffer.toString();
		}
		
	};
	
	
	private ConfigTool(Document baselineXML) {
		
		this.baselineXML = baselineXML;
	}

	
	/**
		use left as the baseline/golden source.
		just print a list like:
		<file being compare> is missing OR has extra OR <field> value is different
		- assume a 120-char width screen
		- 3 columns: Node.attribute, baseline XML, comparee XML
		- only show diffs? or all?
		// if too big for display (say, more than 10 diffs), creates a report in the cur. dir.
		// else show three columns, each line wrapped to 25-30 chars?  
		// the three columns are:
		// Node.attribute 	baselineConfigName 	compareeConfigName		
	*/
	private void compare() {
		
		NodeList childNodes = baselineXML.getDocumentElement().getChildNodes();
		
		int count = 0;

		for (int i = 0; i < childNodes.getLength(); i++) {
			
			Node node = childNodes.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				count++;
			}
			
		}
		
		sort(baselineXML);
		
	}

	private enum Command {
			
			q("Quit"), 
			compare("Compare this to another config.  E.g. compare PC_EQUITIES_UAT"), 
			logdir("Log directory"), 
			grimis("GRIMIS info"), 
			lonweb("LONWEB info"), 
			ports("AC and RMI port information"), 
			nocommand(""),
			find("Find an attribute value.  E.g. find QMdbaxpath"),
			hist("See a history of all commands entered."),
			help("View help");
			
			final String description;

			private Command(String description) {
				this.description = description;
			}

			public static String list() {

				// if a command has no description, it is excluded from the list.
				StringBuffer sb = new StringBuffer();
				for(Command c : values()) {
					if( isNotEmpty(c.description)) sb.append(c.name()).append(" - ").append(c.description).append("\n"); 
				}
				return sb.toString();
			}
	}
	
	
	private void startREPL(String commandString) {
		
		boolean interactive = isEmpty( commandString );
		
		if( interactive ) {	System.out.println( "Config Tool (q to quit, help for command list)" );		}
		
		Reader reader = interactive ? new InputStreamReader( System.in ) : new StringReader( commandString ); 
		
		BufferedReader br = new BufferedReader(reader);
		String cmdString = "";
		
		try {
			
			if( interactive ) {	prompt();	}
			
			while(! ( cmdString = br.readLine()).equalsIgnoreCase("q") ) {
				
				String[] commandElements = cmdString.split(" ");
				Command command = getCommand(commandElements[0]);
				
				commandHistory.add( command.name() );
				
				switch(command) {
		
				
				
					case q:
						System.exit(0);
					case grimis:
						System.out.println( lookupValue("/RiskEngineSetupInfo/QueueManagers/@DefDB") );
						break;
					case lonweb:
						System.out.println( lookupValue("/RiskEngineSetupInfo/QueueManagers/@DBUrl") );
						break;
					case logdir:
						String logdir = lookupValue("/RiskEngineSetupInfo/@Logdir");
						System.out.println( logdir );
						break;
					case compare:

						if(validWith((commandElements.length > 1 && isParsable(commandElements[1])), BAD_XML_MSG)){
							compareeXML = parse(commandElements[1]);
							// TODO: compare, etc
							
						} break;
					case find:

						if(validWith((commandElements.length > 1), INSUFFICIENT_PARAMS_MSG)) {
							// find <this>
							// displays multiple results, etc
							// visit every node and check for @ + name
							//TODO: handle sets of return data
							//TODO: handle nodes
							printWithDefault(lookupValue(commandElements[1]), "No such element found.");
							
						} break;
					case ports:
						StringBuffer sb 
							= new StringBuffer("ac: ")
								.append(lookupValue("/RiskEngineSetupInfo/AppControllers/AppController/@port"))
								.append(", rmi: ").append(lookupValue("/RiskEngineSetupInfo/RMIRegistries/RMIRegistry/@port"));
						
						System.out.println( sb.toString() );
						break;
					case help:
						System.out.println( Command.list() );
						break;
					case hist:
						System.out.println( commandHistory );
						break;
					case nocommand:
					default:
						break;
				}
				
				if( interactive ) {		prompt();	}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private boolean validWith(boolean condition, String defaultString) {
		if(!condition) System.out.println(defaultString);
		return condition;
	}
	
	private void printWithDefault(String main, String defaultString) {
		System.out.println(isNotEmpty(main) ? main : defaultString);
	}
	
	private void prompt() { 	System.out.print("--> ");   }
	
	private String lookupValue(String xPathExpression) {

		// "//@";  
		
        try {
			return (String) XPathFactory.newInstance().newXPath().compile(xPathExpression).evaluate(baselineXML, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			logger.error(e);
			return "";
		}
	}

	List<Node> visited = new ArrayList<Node>();
	
	/**
	 * Credit goes to:
	 * http://myarch.com/treeiter/traditways
	 * @param baseNode
	 * @param idx
	 */
	private void scanTree() {

		Node rootNode = baselineXML.getDocumentElement(); 
		
		Node node = rootNode.getFirstChild();

		while(node != null) {
		
			System.out.println(node.getNodeName());
			
			if (node.hasChildNodes()){
				node = node.getFirstChild();	
			} else {
				
		      while (node.getNextSibling()==null && node != rootNode)
		            node=node.getParentNode();
	
			   node = node.getNextSibling();
			}	
		}
	}	

	private Command getCommand(String in){
		
		try {
			return Command.valueOf(in);
		} catch(IllegalArgumentException e) {
			return Command.nocommand;
		}
	}
	
	
	private static String parseCommands(String[] commandString) {
		
		StringBuffer sb = new StringBuffer();
		
		for(String command : commandString){
			sb.append(command).append("\r\n");
		}
		sb.append("q\r\n");  // quit automatically
		
		return sb.toString();
	}


	private static boolean isParsable(String filename) {
		
		return !StringUtils.isEmpty(filename) && new File(filename).exists() && (parse(filename) != null);
	}

	
	private static Document parse(String filename) {
		
		try {
			
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}	
	}	
	
	
	public static void main(String[] args) {
		
		String leftDocument = (args.length == 0) ? "" : args[0];
		
		if(isParsable(leftDocument)) {

			String commandString = args.length == 1 ? null : parseCommands((String[]) ArrayUtils.subarray(args, 1, args.length));
			new ConfigTool( parse( leftDocument ) ).startREPL(commandString);
//			new ConfigTool( parse( leftDocument ) ).scanTree();
			
		} else {

			StringBuffer usage = new StringBuffer("Usage:\n").append(BAD_XML_MSG);
			System.out.println(usage.toString());
			return;
		}		
	}	
	
	
	
	// -------------- UNUSED -----------------------------------------------------------------------------------
	
	
	private static String buildFullNodePath(Node in) {
		
		if(in.getParentNode() == null) {
			return new StringBuffer().append(in.getNodeName()).toString();
		} else {
			return new StringBuffer().append(buildFullNodePath(in.getParentNode())).append("--").append(in.getNodeType() == (Node.ELEMENT_NODE) ? in.getNodeName() : "").toString();
		}
	}

	//TODO
	private Document sort(Document in) {
		
		Document out = null; 
	
		try {
			
			out = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			Node documentNode = out.appendChild(out.createElement(in.getDocumentElement().getNodeName()));
			
			Set<String> sortedSet = new TreeSet<String>();
			
			for(Node node : getNodeIterator(in.getDocumentElement().getChildNodes())){
				 sortedSet.add(node.getNodeName());
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		System.out.println(out.toString());
		
		return out;
	}

	
	private Iterable<Node> getNodeIterator(final NodeList nodeList){
		
		return new Iterable<Node>(){

			int i = 0;
			
			@Override
			public Iterator<Node> iterator() {

				return new Iterator<Node>(){

					@Override
					public boolean hasNext() {

						return i < nodeList.getLength();
					}

					@Override
					public Node next() {
						
						if(hasNext()){
							return nodeList.item(i++);
						} else {
							throw new NoSuchElementException();
						}
					}

					@Override
					public void remove() {
						// do nothing
					}
				};
			}
		
		};
		
	} 
	
	
	protected void compare(Node baseNode, Node compareeNode) {

		// compare child node counts
		int baseCount = (baseNode.hasChildNodes()) ? baseNode.getChildNodes().getLength() : 0;
		int compareeCount = (compareeNode.hasChildNodes()) ? compareeNode.getChildNodes().getLength() : 0;
		
		writeCountDiff(buildFullNodePath(baseNode), (baseCount - compareeCount));
		
		compareAttributes(baseNode, compareeNode);
	}
	
	
	private void compareAttributes(Node base, Node comparee) {
		
		NamedNodeMap baseAttributes = base.getAttributes();
		NamedNodeMap compareeAttributes = comparee.getAttributes();
		
		writeCountDiff(buildFullNodePath(base) + "'s attributes ", (baseAttributes.getLength() - compareeAttributes.getLength()));

		// TODO: compare all attributes
	}
	
	
	private void writeCountDiff(String what, int diff) {
		
		if(diff != 0) differences.add(new StringBuffer(what).append(" has ").append(diff).append((diff < 0) ? " fewer " : " more ").append("elements than the other.").toString());
	}

	
	protected static void printOutStatistics(Document... docs) {
		
		for(Document doc : docs){

			System.out.println(doc.getDocumentURI());
			
			NodeList childNodes = doc.getDocumentElement().getChildNodes();
			int count = 0;
			for (int i = 0; i < childNodes.getLength(); i++) {
				if(childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					System.out.println(childNodes.item(i).getNodeName());
					count++;
				}
			}
			
			System.out.println("Document has " + count + " nodes.");
			
		}
	}
	
	
	private static void debug(String msg){
		
		if(logger.isDebugEnabled()){
			logger.debug(msg);
		}
	}
	
	private void garbage() {
		
		
		// old main():
/*		if(isParsable(leftDocument) && isParsable(rightDocument)) {

			final ConfigTool configComparisonTool = new ConfigTool( parse(leftDocument), parse(rightDocument) );
			configComparisonTool.compare();
			
		} else {
			System.out.println("Please provide two valid XML documents to compare.");
			return;
		}*/		
	}
	
}
