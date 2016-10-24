package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class DragDrop extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String pattern1 = args[0];
			String pattern2 = args[1];
			Log.info(String.format("Drag and drop from '%s' to '%s'", pattern1, pattern2));
			Element.drapDrop(pattern1, pattern2);
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
