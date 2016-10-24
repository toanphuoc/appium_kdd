package com.s3corp.heat.testng.base;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.xalan.trace.PrintTraceListener;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.config.Config;
import com.s3corp.heat.config.ConfigVar;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.core.sikuli.base.Application;
import com.s3corp.heat.exception.KeywordNotFoundException;
import com.s3corp.heat.helper.obj.TestCase;
import com.s3corp.heat.helper.obj.TestStep;
import com.s3corp.heat.log.Log;
import com.s3corp.heat.testng.report.ConstantReport;
import com.s3corp.heat.testng.report.TestCaseXmlReport;
import com.s3corp.heat.testng.report.TestStepXmlReport;
import com.s3corp.heat.testng.report.XmlReporter;

public class ExecuteTestCase {

	private static ExecuteTestCase executeTestCase;
	private static List<TestStepXmlReport> testStepXml = new ArrayList<TestStepXmlReport>();
	private SimpleDateFormat sdf = new SimpleDateFormat(ConstantReport.DATE_FORMAT);
	private static char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private static boolean breakTestStep = false;
	/**
	 * Get ExecuteTestCase object
	 * 
	 * @return
	 */
	public static ExecuteTestCase getInstanceExecute() {
		if (executeTestCase == null)
			executeTestCase = new ExecuteTestCase();
		return executeTestCase;
	}

	/**
	 * Execute test case
	 * 
	 * @param testCase
	 */
	public void execute(TestCase testCase) {

		String testName = testCase.getName();
		List<TestStep> testSteps = testCase.getTestSteps();

		testStepXml.clear();

		long startTime = System.currentTimeMillis();
		String startTimeStr = sdf.format(new Date(startTime));

		String packageName = ActionBase.class.getPackage().getName();
		
		int stepSize = testSteps.size();
		
		//Flag status result test case
		//boolean result = true;
		
		for (int i = 0; i < stepSize; i++) {
			TestStepXmlReport testStepReporter = new TestStepXmlReport();

			long tsTimeStart = System.currentTimeMillis();
			TestStep testStep = testSteps.get(i);
			String actionName = testStep.getActionName().trim();
			
			try {
				// Set attribute TestStep of report
				testStepReporter.setTestStep(testStep);
				
				String classActionName = null;
				try {
					classActionName = ActionKeyReader.getClassKeywordConfig(actionName).trim();
				} catch (Exception e) {
					if (classActionName == null) {
						throw new KeywordNotFoundException(actionName);
					}
				}
				
				ActionBase actionBase = (ActionBase) Class.forName(packageName + "." + classActionName).newInstance();
				actionBase.perform(testStep.getAgruments());
				
				breakTestStep = actionBase.isFlagStop();
				
				testStepReporter.setResult(actionBase.isResult());
				testStepReporter.setErr(actionBase.getErrMessage());
			} catch (Exception e) {
				testStepReporter.setResult(false);
				testStepReporter.setErr(e.getMessage());
				Log.error(e.getMessage());
				e.printStackTrace();
			} finally {
				// Log next step in console
				if (!actionName.equalsIgnoreCase("<DESC>")) {
					Log.next();
				}
				
				long tsTimeEnd = System.currentTimeMillis();
				double timeDuration = tsTimeEnd - tsTimeStart;
				testStepReporter.setTimeDuration(timeDuration);

				if (!testStepReporter.isResult()) {

					// Capture screenshot
					if (Boolean.valueOf(Config.getConfigProperties(ConfigVar.ANABLE_CAPTURE_SCREENSHOT))) {
						String imgFolder = Config.getConfigProperties(ConfigVar.REPORT_OUTPUT_DIR) + "\\" + ConstantReport.DIR_IMGS_REPORT_FAILED;

						File folder = new File(imgFolder);
						if (!folder.exists()) {
							folder.mkdir();
						}

						// Get absolute path image file
						String imgFile = imgFolder + "\\tc_" + createRandomTestCaseId() + "_" + testStepReporter.getTestStep().getLine() + ".jpg";

						// Take screenshot
						screenShort(imgFile);

						File img = new File(imgFile);
						if (img.exists())
							testStepReporter.setImg(imgFile);
					}
				}

				// Create test step xml report
				testStepXml.add(testStepReporter);
			}
			
			if(breakTestStep){
				i = stepSize - 2;
			}
		}
		long endTime = System.currentTimeMillis();
		String endTimeStr = sdf.format(new Date(endTime));
		double timeDuration = endTime - startTime;
		
		boolean resultTc = checkResultTestCase();
		if(!resultTc){
			// Get log device
			if (Boolean.valueOf(Config.getConfigProperties(ConfigVar.ENABLE_LOG_FILE_DEVICE))) {
				String logFolder = Config.getConfigProperties(ConfigVar.REPORT_OUTPUT_DIR) + "\\" + ConstantReport.DIR_LOG_DEVICE;
				
				//Create log folder if it is NOT exist
				File logDir = new File(logFolder);
				if(!logDir.exists()){
					logDir.mkdir();
				}
				
				String logName = logFolder + "\\";
				//TODO later 
			}
		}
		
		Log.info(String.format("Total: Test case '%s' is %s. Time duration is %sms", testName, resultTc == true ? "PASSED" : "FAILED", timeDuration));
		// Create TestCaseXml for reporter
		TestCaseXmlReport tc = new TestCaseXmlReport(testName, resultTc, startTimeStr, endTimeStr, timeDuration, testStepXml);

		// Generate XML file
		XmlReporter.createXmlTestCaseReport(tc);
	}

	/**
	 * Get URL of images is captured
	 * 
	 * @return
	 */
	private static void screenShort(String fileName) {
		try {
			
			//Mobile screenshot
			if(App.getDriver() != null){
				App.getScreenshot(fileName);
			}
			
			//Desktop screenshot
//			if(Application.getScreen() != null){
//				Application.getScreenshot(fileName);
//			}
			
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
	}

	/**
	 * Check result of test case
	 * 
	 * @return
	 */
	private static boolean checkResultTestCase() {
		for (TestStepXmlReport t : testStepXml) {
			if (!t.isResult()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Create random test step ID
	 * 
	 * @return
	 */
	private static String createRandomTestCaseId() {
		return String.valueOf(System.currentTimeMillis());
	}
}
