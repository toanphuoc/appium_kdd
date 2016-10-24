package com.s3corp.heat.core.appium.element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.WebElement;

import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.log.Log;

import io.appium.java_client.android.AndroidDriver;

public class AndroidAction {

	private static AndroidDriver<WebElement> androidDriver = App.getAndroidDriver();

	public static void initDriver(){
		Log.debug("Init new Android driver");
		androidDriver = App.getAndroidDriver();
	}
	
	/**
	 * Open notification
	 * Platform support: Android
	 */
	public static void openNotification() {
		try {
			androidDriver.openNotifications();
		} catch (Exception e) {
			initDriver();
			androidDriver.openNotifications();
		}
	}
	
	/**
	 * Start activity
	 * @param bundleId
	 * @param activity
	 */
	public static void startActivity(String bundleId, String activity){
		try {
			if(androidDriver != null){
				try {
					androidDriver.startActivity(bundleId, activity);	
				} catch (Exception e) {
					initDriver();
					androidDriver.startActivity(bundleId, activity);	
				}
			}	
			else Log.error("Session ID is NULL");
		} catch (Exception e) {
		}
	}
	
	/**
	 * Get current activity on android platform
	 * @return
	 */
	public static String getCurrentActivity(){
		String activity = null;
		try {
			activity = androidDriver.currentActivity();
		} catch (Exception e) {
			initDriver();
			try {
				activity = androidDriver.currentActivity();
			} catch (Exception e2) {
				Log.error(e2.getMessage());
			}
			
		}
		return activity;
	}
	
	/**
	 * Install .apk file
	 * @param appPath
	 */
	public static void installApp(String appPath){
		if(androidDriver != null)
			try {
				androidDriver.installApp(appPath);
			} catch (Exception e) {
				initDriver();
				androidDriver.installApp(appPath);
			}
			
		else Log.error("Session ID is NULL");
	}
	
	/**
	 * Execute ADB Shell command
	 * @param commands
	 * @return
	 * @throws IOException 
	 */
	public static String executeCommand(String commands) throws IOException{
		commands = "adb shell " + commands;
		
		Process process = Runtime.getRuntime().exec(commands);
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		reader.close();
		Log.debug("Result execute adb command: " + sb.toString());
		return sb.toString();
	}
}
