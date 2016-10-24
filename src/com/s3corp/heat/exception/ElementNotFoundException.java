package com.s3corp.heat.exception;

import org.openqa.selenium.NoSuchElementException;

public class ElementNotFoundException extends NoSuchElementException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElementNotFoundException(String message){
		super(String.format("Location '%s' is NOT exist", message));
	}
	
}
