package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class WaitForText extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ArgumentException{
		super.perform(args);
		if(args.length < 3){
			throw new ArgumentException("Number of argument is invalid");
		}
		try {
			String keyElement = args[0];
			String text = args[1];
			long timeOut = Long.parseLong(args[2]);
			
			Log.info(String.format("Wait for text '%s' at element '%s' appears", text, keyElement));
			ElementAction.waitForText(keyElement, text, timeOut);
		} catch (TimeoutException e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}catch (NoSuchElementException e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}
	}
}
