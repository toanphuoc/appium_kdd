package com.s3corp.heat.helper.obj;

import java.util.Arrays;

public class TestStep {

	private int line;
	private String actionName;
	private String[] agruments;
	
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String[] getAgruments() {
		return agruments;
	}
	public void setAgruments(String[] agruments) {
		this.agruments = agruments;
	}
	
	public String getArgument(){
		return Arrays.toString(agruments);
	}
}
