package junkpile;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


/**
 * The idea behind this was to create a parser with a bit of intelligence;
 * I.e. if the commit message contains multiple JIRA numbers, should it create multiple &lt;action&gt; entries in the changes.xml?
 * This has been abandoned, for the moment. 
 */
public class LogEntryParser implements Iterable<String> {
	
	/**
	 * Letters-single dash-numbers with optional colon, at start of the line
	 */
	public static final Pattern JIRA_REGEX = Pattern.compile("^\\w+-\\d+:?", Pattern.CASE_INSENSITIVE);   
	private String originalLogMessage;
	private List<String> logEntries;
	
	public LogEntryParser(String originalLogMessage) {
		
		super();
		this.originalLogMessage = originalLogMessage;
		parse();
	}

	private void parse() {
		
		logEntries = new LinkedList<String>();

		if( originalLogMessage.isEmpty() || originalLogMessage.contains("[maven-release-plugin]")) {  	return;		} 

		String newString = "";
		
		String[] jiraEntries = originalLogMessage.split( JIRA_REGEX.pattern() );
		
		for(int a = 0; a < jiraEntries.length; a++) {
		
			String jiraEntry = jiraEntries[a];
			
			if( !StringUtils.isEmpty( jiraEntry ) ) { 	

				
				
				
				logEntries.add( newString.replaceAll("\n\n", "\n") );
			}
			
		}				
		
	}


	@Override
	public Iterator<String> iterator() {
		return logEntries.iterator();
	}

}
