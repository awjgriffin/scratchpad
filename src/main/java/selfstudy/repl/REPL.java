package selfstudy.repl;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Vector;

/**
 * <b>R</b>ead <br/>
 * <b>E</b>val<br/>
 * <b>P</b>rint<br/> 
 * <b>L</b>oop<br/>
 * 
 * The starting point for the creation of a simple interpreter / evaluator.
 * 
 * @author grandre
 */
public class REPL {

	final private LinkedList<Command> history 
		= new LinkedList<Command>();
	
	final private Vector<String> validCommands 
		= new Vector<String>(){{add("prn"); add("+"); add("*"); add("-"); add("load file"); add("history");}}; 
	
	
	public void startREPL() {
		
		String in = "";

		BufferedReader isr = new BufferedReader(new InputStreamReader(System.in));
//		Console isr = System.console();

		try {
			
			while( ! (in = isr.readLine()).equalsIgnoreCase("quit") ) {
			
				// need a vocabulary for this language,
				// so we can accept/reject commands
				// consider a macro-like system:
				// 1. you type a command / function defn into the repl.
				// 2. in order to add it to the working system, you need to 
				// 3. convert it into a java class, compile it, and, using the classloader, 
				// 4. load it, thus making it usable.
				// Think of a class in Java as a module in Clojure, it's just a namespace for methods/functions.
				// So you instantiating a class == loading a module.
				// A "load <classname>" will, behind the scenes, do a Class.forName().newInstance or use ClassLoader:
				// ClassLoader loader = new MyEvaluatorClassLoader(host, port);
//				   Object main = loader.loadClass("Main", true).newInstance();
				// how to compile and get a reference to those byte codes?
				
				
				if(validCommands.contains(in)){
					final String cmd = in;
					
					Command command = new Command(){
						
						public void execute() {
							System.out.println(toString());
						}

						@Override
						public String toString() {
							return cmd;
						}
						
					};
					
					history.add(command);
					
					command.execute();
				}
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			System.out.println("Quitting REPL...");
			System.exit(0);
		}
		
		
	} 
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
//		Process process = Runtime.getRuntime().exec("dir ");
//		process.waitFor();
//		System.out.println(process.exitValue());
		
		new REPL().startREPL();
	}
}
