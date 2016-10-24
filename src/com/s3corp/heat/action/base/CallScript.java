package com.s3corp.heat.action.base;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import com.s3corp.heat.action.ActionBase;
import com.s3corp.heat.exception.ArgumentException;
import com.s3corp.heat.exception.AssertException;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.exception.KeywordNotFoundException;
import com.s3corp.heat.helper.TestScriptReader;
import com.s3corp.heat.helper.obj.TestCase;
import com.s3corp.heat.helper.obj.TestStep;
import com.s3corp.heat.log.Log;
import com.s3corp.heat.testng.base.ActionKeyReader;

public class CallScript extends ActionBase{
	
	private String message = null;
	
	@Override
	public void perform(String[] args) throws MalformedURLException,
			DeviceNotFoundException, ArgumentException {
		super.perform(args);
		
		if(args.length < 1){
			throw new ArgumentException("Number of argument is invalid");
		}
		
		try {
			String scriptPath = args[0];
			TestScriptReader  reader = TestScriptReader.getInstance();
			File testFile = new File(TestScriptReader.CURRENT_SCRIPT_FOLDER + "\\" + scriptPath);
			if(!testFile.exists()){
				throw new AssertException(String.format("Script %s is NOT exist", testFile.getAbsolutePath()));
			}
			TestCase testCase = reader.loadObjectTestCase(testFile);
			
			Log.info(String.format("CALL SCRIPT: %s", testCase.getName()));
			
			boolean resultExecuteScript = this.executeCallScript(testCase);
			if(!resultExecuteScript){
				throw new AssertException(message);
			}
		} catch (Exception e) {
			result = false;
			errMessage = subStringErrorException(e.getMessage());
			Log.error(errMessage);
		}
	}
	
	private boolean executeCallScript(TestCase testCase){
		List<TestStep> testSteps = testCase.getTestSteps();
		String packageName = ActionBase.class.getPackage().getName();
		
		boolean rs = true;
		int size = testSteps.size();
		boolean isFlagStop = false;
		for (int i = 0; i < size; i++) {
			TestStep testStep = testSteps.get(i);
			String actionName = testStep.getActionName().trim();
			
			try {
				String classActionName = null;
				try {
					classActionName = ActionKeyReader.getClassKeywordConfig(actionName).trim();
				} catch (Exception e) {
					if (classActionName == null) {
						throw new KeywordNotFoundException(actionName);
					}
				}
				
				ActionBase actionBase = (ActionBase) Class.forName(packageName + "." + classActionName).newInstance();
				actionBase.perform(testStep.getAgruments());
				
				rs = actionBase.isResult();
				
				if(!rs){
					message = actionBase.getErrMessage();
					break;
				}
				
				isFlagStop = actionBase.isFlagStop();
				if(isFlagStop){
					break;
				}
			} catch (Exception e) {
				message = e.getMessage();
				rs = false;
			}finally{
				if(!rs){
					Log.error(message);
					break;
				}
			}
		}
		return rs;
	}
	
	
}
