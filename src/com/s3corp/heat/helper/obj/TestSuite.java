package com.s3corp.heat.helper.obj;

import java.util.List;

public class TestSuite {

	private String name;
	private List<TestCase> testCases;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TestCase> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
}
