package com.s3corp.heat.testng.base;

import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.s3corp.heat.exception.KeywordNotFoundException;
import com.s3corp.heat.helper.obj.TestCase;
import com.s3corp.heat.helper.obj.TestSuite;
import com.s3corp.heat.log.Log;

public class TestngSuite {

	private ExecuteTestCase executeTestCase;
	
	@DataProvider(name="dataKeyWordProvider")
	public Object[][] DataProvider(){
		return new TestngDataProvider().getDataProvider();
	}
	
	@BeforeTest
	public void setUp(){
		executeTestCase = new ExecuteTestCase();
	}
	
	@Test(dataProvider = "dataKeyWordProvider", singleThreaded = true)
	public void executeTest(TestSuite testSuite, String suiteName) throws KeywordNotFoundException{
		List<TestCase> testCases = testSuite.getTestCases();
		for (TestCase testCase : testCases) {
			String caseName = testCase.getName();
			Log.startTestCase(caseName);
			
			executeTestCase.execute(testCase);
			
			Log.endTestCase(caseName);
		}
	}
	
	@AfterTest
	public void tearDown(){
		
	}
}
