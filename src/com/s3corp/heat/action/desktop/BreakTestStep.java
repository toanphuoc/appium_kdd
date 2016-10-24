package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;
import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class BreakTestStep extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String p = args[0];
			boolean flagCheck = true;
			if(args.length == 2){
				flagCheck = Boolean.parseBoolean(args[1]);
			}
			
			boolean exist = Element.checkExist(p);
			if(flagCheck && exist){
				Log.info(String.format("Process is stop because pattern '%s' exists", p));
				flagStop = true;
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
