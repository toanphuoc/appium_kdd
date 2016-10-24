package com.s3corp.heat.action.desktop;

import java.net.MalformedURLException;

import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.core.sikuli.region.Element;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;

public class RightClick extends ActionBase{

	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
//		if(args.length < 1){
//			throw new ArgumentException("Number of argument is invalid");
//		}
		
		try {
			int length = args.length;
			if(length == 0){
				Log.info("Right click at cursor is focusing");
				Element.rightClick();
			}else if(length == 1){
				String p = args[0];
				Log.info(String.format("Right click at locator '%s'", p));
				Element.rightClick(p);
			}else if(length == 2){
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				Log.info(String.format("Right click at point(%d, %d)", x, y));
				Element.rightClick(x, y);
			}
		} catch (Exception e) {
			errMessage = subStringErrorException(e.getMessage());
			result = false;
			Log.error(errMessage);
		}
	}
}
