package com.s3corp.heat.action.base;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.exception.ElementNotFoundException;
import com.s3corp.heat.log.Log;

public class Description extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException, DeviceNotFoundException, ElementNotFoundException, ArgumentException {
		super.perform(args);
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		String desc = args[0];
		Log.desc(desc);
	}
}
