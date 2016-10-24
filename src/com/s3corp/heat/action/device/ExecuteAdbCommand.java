package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.AndroidAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class ExecuteAdbCommand extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String commants = args[0];
			String resultExpected = args[1];
			Log.info(String.format("Execute adb commands '%s'", commants));
			
			String result = AndroidAction.executeCommand(commants);
			if(result == null){
				throw new AssertException("Execute commands adb failed");
			}
			if(!result.toLowerCase().contains(resultExpected.toLowerCase())){
				throw new AssertException(String.format("Verify result adb commands '%s' is incorrect", commants));
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
