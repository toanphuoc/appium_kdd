package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class ClickPattern extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String img = args[0];
			Log.info(String.format("Click at region '%s'", img));
			Element.click(img);
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
