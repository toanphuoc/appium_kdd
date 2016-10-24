package com.s3corp.heat.core.sikuli.os;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Platform {

	private static final String KILL = "taskkill /f /IM ";
	
	/**
	 * Get process name
	 * @param pathName
	 * @return
	 */
	private static String getProcessName(String pathName){
		File file = new File(pathName);
		return file.getName();
	}
	/**
	 * Check application is running
	 * @param name
	 * @return
	 */
	public static boolean checkApplicationIsRunning(String name){
		
		ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
		try {
			Process process = processBuilder.start();
			String tasksList = toString(process.getInputStream());
	        return tasksList.contains(getProcessName(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return false;
	}
	
	
	@SuppressWarnings("resource")
	private static String toString(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        return string;    
    }
	
	/**
	 * Kill application bay terminal
	 * @param processName
	 */
	public static void killProcess(String processName){
		try {
			Runtime.getRuntime().exec(KILL + "\"" + getProcessName(processName) + "\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get IP Address localhost
	 * @return
	 */
	public static String getIpAddress(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "127.0.0.1";
	}
}
