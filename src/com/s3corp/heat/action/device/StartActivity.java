package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.core.appium.element.AndroidAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class StartActivity extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String bundleId = args[0];
			String activity = args[1];
			
			Log.info(String.format("Start activity %s", activity));
			AndroidAction.startActivity(bundleId, activity);
			//App.startActivty(bundleId, activity);
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
