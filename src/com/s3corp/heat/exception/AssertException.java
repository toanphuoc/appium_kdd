package com.s3corp.heat.exception;

public class AssertException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssertException(String message){
		super(message);
		//Log.error(message);
	}

}
