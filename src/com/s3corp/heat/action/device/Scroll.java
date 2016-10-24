package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class Scroll extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String text = args[0];
			Log.info(String.format("Scroll to text '%s'", text));
			ElementAction.scrollTo(text);
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
		
	}
}
