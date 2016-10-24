package com.s3corp.heat.testng.report;

import java.util.List;

public class TestCaseXmlReport {

	private String name;
	private boolean result;
	private String timeStart;
	private String timeEnd;
	private double timeDurration;
	private List<TestStepXmlReport> testStep;
	
	public TestCaseXmlReport(String name, boolean result, String timeStart,
			String timeEnd, double timeDurration,
			List<TestStepXmlReport> testStep) {
		super();
		this.name = name;
		this.result = result;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.timeDurration = timeDurration;
		this.testStep = testStep;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public double getTimeDurration() {
		return timeDurration;
	}

	public void setTimeDurration(double timeDurration) {
		this.timeDurration = timeDurration;
	}

	public List<TestStepXmlReport> getTestStep() {
		return testStep;
	}

	public void setTestStep(List<TestStepXmlReport> testStep) {
		this.testStep = testStep;
	
	}
}

