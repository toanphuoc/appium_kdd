package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class VerifyPattern extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String p = args[0];
			
			Log.info(String.format("Check Survival of '%s'", p));
			
			boolean flagCheck = true;
			if(args.length == 2){
				flagCheck = Boolean.parseBoolean(args[1]);
			}
			
			boolean exist = Element.checkExist(p);
			if(flagCheck && !exist){
				throw new AssertException(String.format("Pattern '%s' doesn't NOT exist", p));
			}
			
			if(!flagCheck && exist){
				throw new AssertException(String.format("Pattern '%s' exists", p));
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
