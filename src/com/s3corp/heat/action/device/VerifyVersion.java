package com.s3corp.heat.action.device;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class VerifyVersion extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String bundId = args[0];
			String versionExpected = args[1];
			Log.info(String.format("Verify app version with '%s'", versionExpected));
			String version = App.getVersionApp(bundId);
			if(!version.equalsIgnoreCase(versionExpected)){
				throw new AssertException("Version expected is incorrect");
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
