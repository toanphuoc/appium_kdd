package com.s3corp.heat.action.device;

import java.net.MalformedURLException;
import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.appium.element.ElementAction;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class DropdownSelect extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 2){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String ele = args[0];
			String value = args[1];
			
			try {
				int index = Integer.parseInt(value);
				ElementAction.selectOption(ele, index);
			} catch (NumberFormatException e) {
				ElementAction.selectOption(ele, value);
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
