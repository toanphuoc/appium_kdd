package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import org.openqa.selenium.NoSuchElementException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class SendKeys extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ArgumentException{
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String findKey = args[0];
			String text = args[1];
			
			Log.info(String.format("Send key '%s' to element '%s'", text, findKey));
			ElementAction.sendKeys(findKey, text);
		} catch (NoSuchElementException e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}
	}
}
