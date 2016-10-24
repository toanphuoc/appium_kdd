package com.s3corp.heat.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import com.s3corp.heat.helper.TestScriptReader;
import com.s3corp.heat.helper.obj.TestSuite;
import com.s3corp.heat.log.Log;
import com.s3corp.heat.testng.base.TestngRunner;

public class Runner {

	public static void main(String[] args) {
		File file = new File("log/logging.log");
		if(file.exists() && file.isFile())
			file.delete();
		
		if(args.length < 1){
			Log.error("Not have test script file");
			return;
		}
		
		String fileName = args[0];
		File scriptFile = new File(fileName);
		if(!scriptFile.exists()){
			Log.error("Script file is not exist");
			return;
		}
		
		if(args.length == 2){
			String datFile = args[1];
			File fDat = new File(datFile);
			if(fDat.exists()){
				Log.debug("Reading data file");
				try {
					readingDataTestSuite(fDat);
				} catch (IOException e) {
					Log.error(e.getMessage());
				}
			}else{
				Log.error("Data file is not exist.");
			}
		}
		
		executeScript(scriptFile);
	}
	
	/**
	 * Read data test suite
	 * @param file
	 * @throws IOException 
	 */
	private static void readingDataTestSuite(File file) throws IOException{
		TestData.testMaps.clear();
		
		Charset UTF8_CHARSET = Charset.forName("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF8_CHARSET));
		
		String str = "";
		while ((str = br.readLine()) != null) {
			if(!str.matches("^\\s*$") && !str.startsWith("<!--")){
				String[] arr = str.split("=");
				String key = arr[0];
				String value = arr[1];
				if(TestData.testMaps.get(key) != null){
					TestData.testMaps.remove(key);
				}
				
				Log.info(String.format("Put value '%s' into key '%s'", value, key));
				TestData.testMaps.put(key, value);
			}
		}
		
		br.close();
	}
	
	/**
	 * Execute script file
	 * @param scriptFile
	 */
	private static void executeScript(File scriptFile) {
		TestScriptReader tsr = new TestScriptReader();
		
		TestSuite testSuite = tsr.loadTestScript(scriptFile);
		TestngRunner.run(testSuite);
	}   
}
