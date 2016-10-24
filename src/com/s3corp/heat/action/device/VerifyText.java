package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class VerifyText extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String find = args[0];
			String expectedValue = args[1];
			
			Log.info(String.format("Verify text at locate '%s' with '%s'", find, expectedValue));
			String value = ElementAction.getTextElement(find);
			if(!expectedValue.toLowerCase().contains(value.toLowerCase()) && !value.toLowerCase().contains(expectedValue.toLowerCase())){
				throw new AssertException(String.format("Verify text '%s' failed because expected value is '%s'", value, expectedValue));
			}
			
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
