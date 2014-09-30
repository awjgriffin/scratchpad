package misc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;


public class MergeUtils {

	private static Properties loadProperties(String filename) throws FileNotFoundException, IOException {
		
		Properties properties = new Properties();
		properties.load(new FileReader(new File(filename)));
		
		return properties;
		
	}
	
	private static Map<String, String> compare(final Properties p1, Properties p2, String filter ) {

		Set keys = new TreeSet() {{	addAll( p1.keySet() ); }};

		Map<String, String> values = new HashMap<String, String>();
		for(Object key : keys ) {
			
			if( key.toString().startsWith( filter ) ) {
				
				String p1Property = StringUtils.trimToEmpty( p1.getProperty( key.toString() ) );
				String p2Property = StringUtils.trimToEmpty( p2.getProperty( key.toString() ) );

				if( !p1Property.equals ( p2Property ) ) {
					values.put( key.toString(),  p1Property.replace(",", " & ") + ","  + p2Property.replace(",", " & "));
				}
			}
		}
		
		return values;
	}
	
	// a map which does not overwrite existing values
	static Map<String, String> myMap = new HashMap<String, String>() {

		private static final String NOT_ADDED="NOT_ADDED";
		
		
		@Override
		public void putAll(Map<? extends String, ? extends String> m) {
			
			for(String key : m.keySet()) {

				if(!keySet().contains(key)) {
					put(key, m.get(key));
				}
			}
		}

		@Override
		public String put(String key, String value) {
			
			if( !keySet().contains(key) ) {
				
				return super.put(key, value);
			} else return NOT_ADDED;
		}
		
		
	};
	
	public static Set<String> commaDelimitedToSet(String commaDelimited) {
		
		Set<String> set = new LinkedHashSet<String>();
		String[] split = commaDelimited.split(",");
		
		for(String s : split) { 		set.add(s);  		}
		
		return set;
	}
	
	public static void compareSets(Set<String> set1, Set<String> set2) {
		
		for(String s : set1) {
			if(!set2.contains(s)) {
				System.out.println(s);
			}
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Set<String> eod = commaDelimitedToSet("EOD,DEAL_NAME,PORT_ID_CLEANED,BOOK_NAME,MEASURE_ID,EXTERNAL_ID,RUN_ID,RISK_GROUP,STRUCTURE_ID,RC_FIELD,SECURITY_NAME,ANALYTICS_PRODUCT_TYPE,VEGA_TYPE_RISK_GROUP,VEGA_TYPE_VT,REPORT_DEF_ID,REPORT_DEF_ID_EX,CURRENCY_ID,INDEX_ID,X,Y,Z,VALUE,NOTIONAL,NOTIONAL_2,NOTIONAL_3,NOTIONAL_4,BOND_TYPE,PORT_ID,TRADE_SOURCE,BOND_REF,IS_NONEQUITYSWAP,X1_SPACE,X2_SPACE,X1_PIPE,X2_PIPE,INDEX_ID1_PIPE,INDEX_ID2_PIPE,MATURITY_DATE,EXPIRY_DATE,PRIMITIVE_TRADE_ID,RUN_TIME,ONE,SOURCE_SYSTEM_BOOK,VARIANT,COMPLEX_PARAM_VEGA_TYPE,EVENTSTRING,BLANK");
		Set<String> intra = commaDelimitedToSet("INTRA,PRIMARY_KEY,DEAL_NAME,PORT_ID_CLEANED,BOOK_NAME,MEASURE_ID,REPORT_DEF_ID,REPORT_DEF_ID_EX,CURRENCY_ID,INDEX_ID,X,Y,Z,VALUE,NOTIONAL,NOTIONAL_2,NOTIONAL_3,NOTIONAL_4,BOND_TYPE,PORT_ID,TRADE_SOURCE,BOND_REF,IS_NONEQUITYSWAP,MATURITY_DATE,STRUCTURE_ID,RC_FIELD,SECURITY_NAME,ANALYTICS_PRODUCT_TYPE,EXPIRY_DATE,PRIMITIVE_TRADE_ID,X1_SPACE,X2_SPACE,X1_PIPE,X2_PIPE,INDEX_ID1_PIPE,INDEX_ID2_PIPE,RUN_TIME,ONE,SOURCE_SYSTEM_BOOK,VARIANT,COMPLEX_PARAM_VEGA_TYPE,EXTERNAL_ID,RUN_ID,RISK_GROUP");
		compareSets(eod,intra);
		System.out.println("");
		compareSets(intra, eod);
		
//		getFileProperties();
		
/*
		Properties eod = loadProperties("C:\\Users\\Public\\Documents\\workspace\\EOD-pumper\\es-rs\\rs-properties\\prod\\grimis-mapping-prod.properties");
		Properties id = loadProperties("C:\\Users\\Public\\Documents\\workspace\\grimis-pumper-EOD_ID_MERGE\\grimis-pumper-config\\src\\main\\resources\\env\\transformer.properties");
		myMap.put("EOD", "ID");
		myMap.putAll( compare(eod, id, "resultType") );
		myMap.put("ID", "EOD");
		myMap.putAll( compare(id, eod, "resultType") );
		myMap.putAll( compare(id, eod, "query.ZPV") );
		for(String key : myMap.keySet() ) {  			System.out.println( key + "," + myMap.get(key) );   		}
*/
		
	}

	private static void getFileProperties() throws IOException {
		File f = new File("C:\\Users\\grandre\\useful docs\\HOW TO\\HOW TO deploy a new Risk Engine version.txt");
		System.out.println(f.getAbsolutePath());
		System.out.println(f.getAbsoluteFile());
		System.out.println(f.getCanonicalPath());
		System.out.println(f.getCanonicalFile());
		System.out.println(f.getParent());
		System.out.println(f.getName());
		System.out.println(f.getPath());
	}
	
}
