package com.s3corp.heat.exception;

public class DeviceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceNotFoundException(String device){
		super(String.format("Devices %s is not found in data excel file.", device));
		
	}
}
