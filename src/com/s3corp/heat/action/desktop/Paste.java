package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class Paste extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			int length = args.length;
			if(length == 1){
				String key = args[0];
				Log.info(String.format("Paste text '%s' at cursor is focusing", key));
				if(key.toLowerCase().startsWith("<#")){
					key = super.getValueFromTestSuiteData(key);
				}
				Element.paste(key);
			}else if(length == 2){
				
				String key = args[1];
				if(key.toLowerCase().startsWith("<#")){
					key = super.getValueFromTestSuiteData(key);
				}
				Log.info(String.format("Paste text '%s' at locator '%s'", key, args[0]));
				Element.paste(args[0], key);
			}
			
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
