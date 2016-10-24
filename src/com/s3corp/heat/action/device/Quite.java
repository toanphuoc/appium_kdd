package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.exception.ElementNotFoundException;
import com.s3corp.heat.log.Log;

public class Quite extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ElementNotFoundException, ArgumentException{
		super.perform(args);
		
		Log.info("Close connection");
		try {
			App.closeConnection();
		} catch (Exception e) {
		}
		
	}
}
