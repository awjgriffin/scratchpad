package misc;

import java.io.InputStream;
import java.util.StringTokenizer;

public class ScriptRunner {

	/* Default script interpreter used by ScriptRunner. */
	private static final String DEFAULT_INTERPRETER = "/bin/ksh";

	private static ScriptRunner instance;

	private int m_nStatus;
	private StringBuffer m_sbOutput;

	public static ScriptRunner getInstance() {
		return (instance == null) ? new ScriptRunner() : instance;
	}

	private ScriptRunner() {
		m_sbOutput = new StringBuffer();
	}

	public String getInterpreter() {
		return DEFAULT_INTERPRETER;
	}

	public int getStatus() {
		return m_nStatus;
	}

	public StringBuffer getOutput() {
		return m_sbOutput;
	}

	public boolean launchScript(String pstrCommand, boolean bStoreOutput) {
		boolean bStatus = true;
		boolean printStdOut = false;

		m_sbOutput = new StringBuffer();

		//
		// Runtime object to execute system call
		Runtime launchCommand = Runtime.getRuntime();
		Process processOut = null;

		String strLaunchCommand = getInterpreter() + " " + pstrCommand;

		System.out.println("Launching command: " + strLaunchCommand );
		
		try {
			processOut = launchCommand.exec( strLaunchCommand );
		} catch (Exception e) {
			String msg = "Exception launching command script ";
			System.err.println(msg + " " + e.getMessage());
			bStatus = false;
		}

		InputStream stdOut = processOut.getInputStream();
		InputStream stdErr = processOut.getErrorStream();

		StringBuffer stdOutBuffer = new StringBuffer();
		StringBuffer stdErrorBuffer = new StringBuffer();

		if (bStatus) {

			new InputStreamHandler(stdOutBuffer, stdOut);
			new InputStreamHandler(stdErrorBuffer, stdErr);

			System.out.println("Waiting for command to complete...");

			// Wait for this script to finish
			try {
				processOut.waitFor();
			} catch (Exception e) {
				String msg = "Exception whilst running command ";
				System.err.println(msg + " " + e.getMessage());
				bStatus = false;
			}

			if (bStoreOutput) {
				// bStatus = storeOutput(processOut);
				this.m_sbOutput = stdOutBuffer;
				System.out.println("Output from script. " + this.m_sbOutput.toString());
			}

			this.m_nStatus = processOut.exitValue();

			if (0 == this.m_nStatus) {
				//
				// Success
				System.out.println("Command script completed successfully");
				// print out the output from the command line
				printStdOut = true;
			} else {
				// Failure - the script returned an error status. Two cases to
				// consider:
				// 1) exit value is 1. In that case we need to print out the
				// error from standard out stream.
				// 2) exit value is not one. Print out the error from standard
				// error stream.
				// changed PRN 17-Nov-2003. In this case (2) it would also be
				// useful to see stdout too.
				System.out.println("Launch command " + strLaunchCommand
						+ " failed with error " + m_nStatus);
				bStatus = false;
				if (1 == this.m_nStatus) {
					// AppLog.logError(m_strClassName, strFunctionName,
					// "The script failed with status 1. See log file for details.");
					printStdOut = true;
				} else {
					printStdOut = true; // added PRN 17-Nov-2003
					// Process the output of the command to get the last output
					// line
					String msg = "The script failed to complete. See log file for details.";
					System.err.println(msg);
				}
			}

			String lastOutput = "";
			if (printStdOut) {
				System.out.println("Output from the standard output stream command script was as follows...");

				System.out.println(stdOutBuffer);
				if (1 == this.m_nStatus) {
					// this is slow and lazy...
					StringTokenizer newLineTokenizer = new StringTokenizer(
							stdOutBuffer.toString(), "\n");
					while (newLineTokenizer.hasMoreTokens()) {
						lastOutput = newLineTokenizer.nextToken();
					}
					System.err.println(lastOutput);
				}
			}

			System.out.println("Output from the standard error stream command script was as follows...");
			System.out.println(stdErrorBuffer);

		}

		return bStatus;
	}
	
	
	public static void main(String[] args) {
		
		if(args.length == 0) { 			
			System.exit(1);
		}
		
		ScriptRunner scriptRunner = ScriptRunner.getInstance();
		
		scriptRunner.launchScript(args[0], true);
		
		
	}

}