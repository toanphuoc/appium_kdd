package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import org.openqa.selenium.NoSuchElementException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.exception.ElementNotFoundException;
import com.s3corp.heat.log.Log;

public class Tap extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ElementNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		try {
			String ele = args[0];
			Log.info(String.format("Tap at element '%s'", ele));
			ElementAction.tap(ele);
		} catch (NoSuchElementException e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}
		
	}
}
