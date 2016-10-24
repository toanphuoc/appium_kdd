package com.s3corp.heat.testng.base;

import java.io.File;

import org.testng.TestNG;

import com.s3corp.heat.config.Config;
import com.s3corp.heat.config.ConfigVar;
import com.s3corp.heat.helper.obj.TestSuite;
import com.s3corp.heat.testng.listeners.IReporterCustom;
import com.s3corp.heat.testng.listeners.MyIInvokedMethodListener;
import com.s3corp.heat.testng.listeners.MyITestListeners;
import com.s3corp.heat.testng.listeners.MyTestListenerAdapter;

public class TestngRunner {

	private static TestSuite testSuite;
	
	public static TestSuite createTestngDataProvider(){
		return testSuite;
	}
	
	/**
	 * Constructor TestNG object
	 * @param testSuite
	 */
	public static void run(TestSuite testSuite){
		
		TestngRunner.testSuite = testSuite;
		
		//Read report output folder
		String reportDir = Config.getConfigProperties(ConfigVar.REPORT_OUTPUT_DIR);
		
		//Create create folder output if it's not exist
		File fReportDir = new File(reportDir);
		if(!fReportDir.exists()){
			fReportDir.mkdir();
		}
		
		TestNG testNG = new TestNG();
		testNG.setOutputDirectory(fReportDir.getAbsolutePath());
		testNG.addListener(new IReporterCustom());
		testNG.addListener(new MyIInvokedMethodListener());
		testNG.addListener(new MyTestListenerAdapter());
		testNG.addListener(new MyITestListeners());
		testNG.setDefaultSuiteName(testSuite.getName());
		testNG.setTestClasses(new Class[]{TestngSuite.class});
		testNG.run();
	}
}
