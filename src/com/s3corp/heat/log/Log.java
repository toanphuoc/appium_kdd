package com.s3corp.heat.log;

import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName());

	public static void startTestCase(String sTestCaseName) {

		Log.info("****************************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$ " + sTestCaseName
				+ " $$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("****************************************************************************************");
		Log.info("");
	}

	// This is to print log for the ending of the test case
	public static void endTestCase(String sTestCaseName) {
		Log.info("");
		Log.info("*****************************************************************************************");
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX-- END --XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("X");
		
	}

	// Need to create these methods, so that they can be called
	public static void info(String message) {
		Log.info(message);
	}
	
	public static void next(){
		Log.info("----------------------------------------------------------------------------------------");
	}

	public static void warn(String message) {
		Log.warn(message);
	}

	public static void error(String message) {
		Log.error(message);
		//Log.info("----------------------------------------------------------------------------------------");
	}

	public static void fatal(String message) {
		Log.fatal(message);
		System.exit(0);
	}

	public static void debug(String message) {
		Log.debug(message);
	}
	
	public static void desc(String description){
		Log.info("<Description>	" + description);
	}
}
