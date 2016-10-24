package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import org.openqa.selenium.TimeoutException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.exception.ElementNotFoundException;
import com.s3corp.heat.log.Log;

public class WaitFor extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ElementNotFoundException, ArgumentException{
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		try {
			String key = args[0];
			long timeOut = Long.parseLong(args[1]);
			Log.info(String.format("Wait for element '%s' to clickable", key));
			ElementAction.waitFor(key, timeOut);
		} catch (NumberFormatException e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		} catch(TimeoutException e){
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}
		
	}
}
