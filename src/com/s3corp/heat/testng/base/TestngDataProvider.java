package com.s3corp.heat.testng.base;

import com.s3corp.heat.helper.obj.TestSuite;

public class TestngDataProvider {

	public Object[][] getDataProvider(){
		TestSuite testSuite =  TestngRunner.createTestngDataProvider();
		return new Object[][]{{testSuite, testSuite.getName()}};
	}
}
