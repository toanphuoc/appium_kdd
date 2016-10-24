package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class LaunchApplication extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String idDevice = args[0];
			String bunbleId = args[1];
			
			String appActivity = null;
			String appActivityWait = null;
			
			if(args.length == 3){
				appActivity = args[2];
			}
			
			if(args.length == 4){
				appActivity = args[2];
				appActivityWait = args[3];
			}

			Log.info(String.format("Launch application '%s' on '%s' device", bunbleId, idDevice));
			App.launchApplication(idDevice, bunbleId, appActivity, appActivityWait);
		} catch (Exception e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}
	}
}
