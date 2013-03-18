package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public class HttpUtils {

	public static String getWebPageAsText(String urlString) throws Exception {
		
		URL url = new URL( urlString );
		
		StringWriter sw = new StringWriter();
		BufferedReader bis = new BufferedReader( new InputStreamReader( url.openStream() ) );
		
		String line;
		while( (line = bis.readLine()) != null ) {
			sw.write(line);
		}

		bis.close();
		
		return sw.toString();
	}

	
	/**
	 * Indiscriminate removal of all tags.
	 * @param htmlIn
	 * @return
	 */
	public static String removeAllTags(String htmlIn){
		return htmlIn.replaceAll("<[^<]+>", "");
	}
	
	public interface HTMLCleaner {
		
		/**
		 * Indiscriminate removal of all tags.
		 * @param htmlIn
		 * @return
		 */
		HTMLCleaner removeAllTags();
		
		/**
		 * Decodes a small subset of HTML escape codes.
		 * @return
		 */
		HTMLCleaner decode();
		
		HTMLCleaner replaceAll(String in, String replacement);
	}

	final public static HTMLCleaner getCleaner(final String in) {
		
		return new HTMLCleaner() {

			private String string = in;
			
			@Override
			public HTMLCleaner removeAllTags() {
				string = HttpUtils.removeAllTags(string);
				return this;
			}

			@Override
			public HTMLCleaner decode() {

				string = string.replaceAll("&nbsp;", " ");
				string = string.replaceAll("&amp;", "&");
				string = string.replaceAll("&gt;", ">");
				string = string.replaceAll("&lt;", "<");
				string = string.replaceAll("&quot;", "\"");
				string = string.replaceAll("&apos;", "\'");
				return this;
			}
			
			@Override
			public HTMLCleaner replaceAll(String in, String replacement) {

				string = string.replaceAll(in, replacement);
				return this;
			}

			@Override
			public String toString() {
				return string;
			}
			
		};
		
	}
	
}
