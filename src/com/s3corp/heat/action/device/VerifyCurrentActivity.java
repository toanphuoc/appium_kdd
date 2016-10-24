package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.core.appium.element.AndroidAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class VerifyCurrentActivity extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String activityExpected = args[0];
			Log.info(String.format("Verify current activity with %s", activityExpected));
			
			String currentActivity = AndroidAction.getCurrentActivity();
			boolean check = true;
			if(args.length == 2){
				try {
					check = Boolean.parseBoolean(args[1]);
				} catch (Exception e) {
				}
			}
		
			if(currentActivity == null){
				throw new AssertException("Could not get current activity");
			}
			
			if((check && !currentActivity.equals(activityExpected)) || (check && currentActivity == null)){
				throw new AssertException(String.format("Current activty %s is incorrect", activityExpected));
			}
			
			if(!check && currentActivity.equals(activityExpected)){
				throw new AssertException(String.format("Current activty %s is incorrect", activityExpected));
			}
			
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
