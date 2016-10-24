package com.s3corp.heat.exception;

import java.net.MalformedURLException;

public class AppiumURLException extends MalformedURLException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppiumURLException(String url){
		super(String.format("URL '%s' is invalid", url));
	}
}
