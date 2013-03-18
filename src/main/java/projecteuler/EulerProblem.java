package projecteuler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import utils.HttpUtils;

/**
 * @see http://projecteuler.net/problems
 * 
 * @author grandre
 */
public abstract class EulerProblem {

	
	@Test
	final public void testProblem() { 
		
		System.out.println("Problem description:\n" + problemDescription() + "\n\n");

		timedSolution();
		
	}; 
	

	final protected void timedSolution() {
		
		long startTime = System.currentTimeMillis();

		System.out.println("Solution:\n");				

		solution();
		
		System.out.println(String.format("Solution took %s to complete.", calcTime(startTime) ) );
	}
	
	final private String calcTime(long startTime) {
		
		long ms = System.currentTimeMillis() - startTime;
		return (ms < 1000) ? ms + " ms" : (ms/1000) + " s";
	}

	/**
	 * This should be overridden with the actual computation/algorithm etc.
	 * It should <code>System.out.println</code> the solution also.
	 */
	public abstract void solution();

	/**
	 * This can be overridden, but if the implementing problem class is named like &quot;Problem&lt;num&gt;&quot;, then it needn't be, because
	 * the problem description will be retrieved from the <code>http://projecteuler.net</code> web site and displayed automatically.
	 * @return
	 */
	public String problemDescription() {
		
		// these need to be set; update if they become out-dated.
		setSystemPropertyWithDefault("http.proxyHost", "proxy-hbr.gslb.db.com");
		setSystemPropertyWithDefault("http.proxyPort", "8080");
		
		StringBuffer sb = new StringBuffer();
		
		try {
			
			String webPageAsText = HttpUtils.getWebPageAsText(("http://projecteuler.net/problem=" + this.getClass().getName().split("Problem")[1]));
			
			Matcher matcher = Pattern.compile("<div class=\"problem_content\" role=\"problem\">(.*)<div id=\"footer\" class=\"noprint\">").matcher(webPageAsText);
			
			while(matcher.find() ){
				sb.append(matcher.group(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			sb.append("Could not retrieve problem description due to exception: \n").append(e.getMessage());
		} 
		
		return HttpUtils.getCleaner(sb.toString()).replaceAll("<br />", "\n").replaceAll("<p>", "\n").removeAllTags().toString();
	}

	
	protected void setSystemPropertyWithDefault(String key, String def) {
		
		System.setProperty(key, System.getProperty(key, def));
	}
}
