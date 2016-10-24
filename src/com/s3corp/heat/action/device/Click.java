package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import org.openqa.selenium.NoSuchElementException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class Click extends ActionBase{

	@Override
	public void perform(String[] args) throws  MalformedURLException, DeviceNotFoundException, ArgumentException{
		super.perform(args);
			
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String keyFind = args[0];
			Log.info(String.format("Click element '%s'", keyFind));
			ElementAction.click(keyFind);
		} catch (NoSuchElementException e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
		
	}
}
