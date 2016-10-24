package com.s3corp.heat.exception;

import com.s3corp.heat.log.Log;

public class KeywordNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KeywordNotFoundException(String keyWord){
		super(String.format("Keyword %s is not found", keyWord));
		Log.error(String.format("Keyword %s is not found", keyWord));
	}
}
