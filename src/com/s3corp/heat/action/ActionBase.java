package com.s3corp.heat.action;

import java.net.MalformedURLException;

import com.s3corp.heat.common.TestData;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;

public abstract class ActionBase {
	
	/**
	 * Result of any test step
	 */
	protected boolean result = true;
	
	/**
	 * Error message
	 */
	protected String errMessage;
	
	/**
	 * Stop progress test case
	 */
	protected boolean flagStop = false;

	/**
	 * Perform agruments  test step 
	 * @param args
	 * @throws MalformedURLException
	 * @throws DeviceNotFoundException
	 */
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ArgumentException{
		
	}
	
	/**
	 * SubString error string exception from first string to '\n'
	 * @param error
	 * @return
	 */
	protected String subStringErrorException(String error){
		int i = error.indexOf("\n");
		if( i == -1)
			i = error.length() - 1;
		return error.substring(0, i);
	}
	
	protected String getValueFromTestSuiteData(String key){
		String k = key.replace("<#", "").replace(">", "");
		return TestData.testMaps.get(k);
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	public boolean isFlagStop() {
		return flagStop;
	}

	public void setFlagStop(boolean flagStop) {
		this.flagStop = flagStop;
	}
}
