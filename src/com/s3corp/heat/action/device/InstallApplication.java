package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.core.appium.element.AndroidAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class InstallApplication extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ArgumentException{
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
	
		try {
			if(args.length == 1){
				String appPath = args[0];
				Log.info(String.format("Install %s app", appPath));
				AndroidAction.installApp(appPath);
			}else if(args.length == 2){
				String device = args[0];
				String appName = args[1];
				
				Log.info(String.format("Install application '%s' on '%s' device", appName, device));
				App.installApplication(device, appName);
			}
			
		} catch (Exception e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}
		
	}
}
