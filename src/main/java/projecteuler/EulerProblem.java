package projecteuler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @see http://projecteuler.net/problems
 * 
 * @author grandre
 */
public abstract class EulerProblem {

	
	@Test
	public void testProblem() { 
		
		System.out.println("Problem description:\n" + problemDescription() + "\n\n");

		timedSolution();
		
	}; 
	

	protected void timedSolution() {
		
		long startTime = System.currentTimeMillis();

		System.out.println("Solution:\n");				

		solution();
		
		System.out.println(String.format("Solution took %s to complete.", calcTime(startTime) ) );
	}
	
	private String calcTime(long startTime) {
		
		long ms = System.currentTimeMillis() - startTime;
		return (ms < 1000) ? ms + " ms" : (ms/1000) + " s";
	}

	/**
	 * This should be overridden with the actual computation/algorithm etc.
	 * It should <code>System.out.println</code> the solution also.
	 */
	public abstract void solution();

	public String problemDescription(){
		
		System.setProperty("http.proxyHost", "proxy-hbr.gslb.db.com");
		System.setProperty("http.proxyPort", "8080");
		
		StringBuffer sb = new StringBuffer();
		
		try {
			
			URL url = new URL("http://projecteuler.net/problem=" + this.getClass().getName().split("Problem")[1]);
			
/*			BufferedReader bis = new BufferedReader( new InputStreamReader( url.openStream() ) );
			
			String line = "";
			while( (line = bis.readLine()) != null ) {
				System.out.println(line);
			}

			bis.close();*/
			
/*			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.toURI().toString());
			NodeList elementsByTagName = document.getDocumentElement().getElementsByTagName("div");
			// class = problem_content, role = problem.
			// change <br> to \n
			for(int i = 0; i < elementsByTagName.getLength(); i++ ) {
				
				Node node = elementsByTagName.item(i);
				if(node.getAttributes().getNamedItem("role") != null && node.getAttributes().getNamedItem("role").equals("problem")){
					System.out.println( node.getTextContent());
				}
			}*/
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			sb.append("Could not retrieve problem description due to exception: \n").append(e.getMessage());
		} 
		
		return sb.toString();
	}
	
}
