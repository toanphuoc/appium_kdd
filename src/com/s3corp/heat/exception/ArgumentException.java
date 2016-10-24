package com.s3corp.heat.exception;

import com.s3corp.heat.log.Log;

public class ArgumentException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArgumentException(String message){
		super(message);
		Log.error(message);
	}
}
