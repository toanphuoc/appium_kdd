package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class ActionKey extends ActionBase{
	
	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String key = args[0];
			int repeat = 1;
			if(args.length == 2){
				repeat = Integer.parseInt(args[1]);
			}
			
			Log.info(String.format("Send action key '%s'", key));
			if(key.toLowerCase().startsWith(Element.FLAG_KEY.toLowerCase())){
				Element.sendKeyAction(key, repeat);
			}else{
				if(key.toLowerCase().startsWith("<#")){
					key = super.getValueFromTestSuiteData(key);
				}
				Element.sendTextAction(key);
			}
			
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
