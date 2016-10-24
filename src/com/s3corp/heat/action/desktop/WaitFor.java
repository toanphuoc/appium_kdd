package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class WaitFor extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String pattern = args[0];
			Log.info(String.format("Wait for locator '%s' appears in %s s", pattern, args[1]));
			double timeOut =  0d;
			try {
				timeOut = Double.parseDouble(args[1]);
			} catch (Exception e) {
				throw new AssertException(e.getMessage());
			}
			Element.waitFor(pattern, timeOut);
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
