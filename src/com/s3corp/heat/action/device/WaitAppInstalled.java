package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class WaitAppInstalled extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String bundleId = args[0];
			Log.info(String.format("Wait app '%s' will be installed", bundleId));
			float timeOut = Float.parseFloat(args[1]);
			boolean flagCheck = true;
			
			if(args.length == 3){
				try {
					flagCheck = Boolean.parseBoolean(args[2]);
				} catch (Exception e) {
					
				}
			}
			boolean rs = App.waitForAppInstalled(bundleId, timeOut);
			
			if(rs && !flagCheck){
				throw new AssertException(String.format("App '%s' is installed after %fs", bundleId, timeOut));
			}
			
			if(!rs && flagCheck){
				throw new AssertException(String.format("App '%s' is still not installed after %fs", bundleId, timeOut));
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
