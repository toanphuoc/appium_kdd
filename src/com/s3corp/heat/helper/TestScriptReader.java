package com.s3corp.heat.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.s3corp.heat.helper.obj.TestCase;
import com.s3corp.heat.helper.obj.TestStep;
import com.s3corp.heat.helper.obj.TestSuite;
import com.s3corp.heat.log.Log;


public class TestScriptReader {

	private static final String TEST_SUITE = "SUITE_NAME";
	
	private static final String TEST_NAME = "TEST_NAME";
	
	private static final String TEST_ID = "TEST_ID";
	
	private static TestScriptReader testScriptReader;
	
	//Default current script folder "test-script"
	public static String CURRENT_SCRIPT_FOLDER = getCurrentPath() + "\\test-script\\";
	
	public static TestScriptReader getInstance(){
		if(testScriptReader == null){
			testScriptReader = new TestScriptReader();
		}
		
		return testScriptReader;
	}
	
	/**
	 * Get directory project path
	 * @return
	 */
	public static String getCurrentPath(){
		File currDir = new File(".");
	    String path = currDir.getAbsolutePath();
	    return path;
	}
	
	/**
	 * Read test script from file
	 * @param pathFile
	 * @return TestScript object
	 */
	public TestSuite loadTestScript(File file){
		
		//Get file extension
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
		TestSuite ts = new TestSuite();
		if(extension.equals("scp")){
			TestCase tc = loadObjectTestCase(file);
			ts.setName(tc.getName());
			List<TestCase> testCases = new ArrayList<TestCase>();
			testCases.add(tc);
			ts.setTestCases(testCases);
		}else if(extension.equals("sco")){
			ts = loadObjectTestSuite(file);
		}else{
			Log.error("File extension is incorrect");
		}
		return ts;
	}
	
	/**
	 * Load object TestSuite
	 * @param file 
	 * @return TestSuite object
	 */
	public TestSuite loadObjectTestSuite(File file){
		
		try {
			Charset UTF8_CHARSET = Charset.forName("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF8_CHARSET));
			String tr = "";
			TestSuite ts = new TestSuite();
			List<TestCase> testCases = new ArrayList<TestCase>();
			
			int line = 1;
			while((tr = br.readLine()) != null){
				if(tr.contains(TestScriptReader.TEST_SUITE)){
					String[] temps = tr.split("\t");
					ts.setName(temps[1].trim());
				}else{
					if(!tr.matches("^\\s*$")){
						String absPath = file.getParent();
						File tcFile  = new File(absPath + "\\" + tr);
						if(tcFile.exists() && (FilenameUtils.getExtension(tcFile.getAbsolutePath())).equals("scp")){
							TestCase tc = loadObjectTestCase(tcFile);
							testCases.add(tc);
						}else{
							Log.fatal(String.format("File %s is NOT exist at line %s", tr, line));
						}
					}
				}
				line ++;
			}
			br.close();
			ts.setTestCases(testCases);
			return ts;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Load testCase Object
	 * @param file
	 * @return TestCase object
	 */
	public TestCase loadObjectTestCase(File file){
		try {
			Charset UTF8_CHARSET = Charset.forName("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF8_CHARSET));
			String tr = "";
			
			TestCase testCase = new TestCase();
			List<TestStep> testSteps = new ArrayList<TestStep>();
			
			int line = 1;
			while((tr = br.readLine()) != null){
				if(tr.contains(TestScriptReader.TEST_NAME)){
					//Set Test script name
					String[] temps = tr.split("\t");
					testCase.setName(temps[1].trim());
				}else if(tr.contains(TestScriptReader.TEST_ID)){
					
					//Set test script id
					String[] temps = tr.split("\t");
					testCase.setId(temps[1].trim());
				}else{
					//Set test script keywords array
					if(!tr.matches("^\\s*$")){
						String[] arr = tr.split("\t");
						TestStep step = new TestStep();
						step.setLine(line);
						step.setActionName(arr[0]);
						
						step.setAgruments(Arrays.copyOfRange(arr, 1, arr.length));
						testSteps.add(step);
					}
					
				}
				line++;
			}
			testCase.setTestSteps(testSteps);
			br.close();
			return testCase;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
