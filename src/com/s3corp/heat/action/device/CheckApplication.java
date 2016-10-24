package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class CheckApplication extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String bundleId = args[0];
			boolean flag = true;
			try {
				flag = Boolean.parseBoolean(args[1]);
			} catch (Exception e) {
			}
			
			Log.info(String.format("Check application '%s' is installed", bundleId));
			
			boolean isInstalled = App.isInstalled(bundleId);
			
			if(flag && !isInstalled){
				throw new AssertException(String.format("Application '%s' is NOT installed", bundleId));
			}
			
			if(!flag && isInstalled){
				throw new AssertException(String.format("Application '%s' is still installed", bundleId));
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
