package com.s3corp.heat.testng.report;

import com.s3corp.heat.helper.obj.TestStep;

public class TestStepXmlReport {

	private TestStep testStep;
	private boolean result = true;
	private String err;
	private String img;
	private double timeDuration;
	
	public TestStepXmlReport(){
		
	}
	
	public TestStepXmlReport(TestStep testStep, boolean result, String err,
			String img, double timeDuration) {
		super();
		this.testStep = testStep;
		this.result = result;
		this.err = err;
		this.img = img;
		this.timeDuration = timeDuration;
	}

	public TestStep getTestStep() {
		return testStep;
	}

	public boolean isResult() {
		return result;
	}

	public String getErr() {
		return err;
	}

	public String getImg() {
		return img;
	}

	public double getTimeDuration() {
		return timeDuration;
	}

	public void setTestStep(TestStep testStep) {
		this.testStep = testStep;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setTimeDuration(double timeDuration) {
		this.timeDuration = timeDuration;
	}
	
	
}
