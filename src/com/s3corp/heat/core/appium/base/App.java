package com.s3corp.heat.core.appium.base;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.s3corp.heat.config.Config;
import com.s3corp.heat.config.ConfigVar;
import com.s3corp.heat.exception.DeviceNotFoundException;
import com.s3corp.heat.log.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class App {

	private static AppiumDriver<?> driver;
	
	/**
	 * Flag user iOS device
	 */
	private final static String FLAG_IOS_DEVICE = "iOS";
	
	/**
	 * Flag use Android device
	 */
	private static final String FLAG_ANDROID_DEVICE = "Android";
	
	/**
	 * CHROME name browser
	 */
	private static final String CHROME_BROWSER = "Chrome";
	
	/**
	 * Device information value
	 */
	private static AppiumCapabilities appiumCapabilities;
	
	/**
	 * Flag difference between iOS driver or android driver
	 */
	private static boolean flagIOSDriver = false;
	
	/**
	 * Application name value
	 */
	private static String appName;
	
	/**
	 * NATIVE context
	 */
	private static final String NATIVE_CONTEXT = "NATIVE_APP";
	
	/**
	 * directory idDeviceInstaller on OSX machine
	 */
	private static String IDEVICEINSTALLER = "/usr/local/bin/ideviceinstaller";
	
	private static String VERSION_NAME = "versionName";
	/**
	 * Get Application Name
	 * @return
	 */
	public static String getApp(){
		return appName;
	}
	
	/**
	 * Get Information device
	 * @return
	 */
	public static AppiumCapabilities getInforDevice(){
		return appiumCapabilities;
	}
	/**
	 * Check ios driver
	 * @return
	 */
	public static boolean getFlagIOSDriver(){
		return flagIOSDriver;
	}
	/**
	 * Get Driver Appium
	 * @return
	 */
	
	public static AppiumDriver<?> getDriver(){
		return driver;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static AndroidDriver<WebElement> getAndroidDriver(){
		return (AndroidDriver<WebElement>) driver;
	}
	
	/**
	 * Check iOS device
	 * @param idDevice
	 * @return
	 */
	private static boolean isIOSDevice(String osPlatform){
		return osPlatform.equalsIgnoreCase(FLAG_IOS_DEVICE);
	}
	
	/**
	 * Check android device
	 * @param idDevice
	 * @return
	 */
	private static boolean isAndroidDevice(String osPlatform){
		return osPlatform.equalsIgnoreCase(FLAG_ANDROID_DEVICE);
	}
	
	/**
	 * Launch Application By Device and bundleId
	 * @param idDevice
	 * @param bundleId
	 * @return
	 * @throws Exception
	 */
	public static AppiumDriver<?> launchApplication(String idDevice, String bundleId, String appActivity, String appActivityWait) throws Exception{
		//Set Application name
		if(appName == null)
			appName = bundleId;
		
		//Get device information from device ID
		if(appiumCapabilities == null)
			getCapabilities(idDevice);
		
		//Throw exception when device information is NULL
		if(appiumCapabilities == null){
			throw new DeviceNotFoundException(idDevice);
		}
		
		URL url = new URL(appiumCapabilities.getUrlHub());
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		//For iOS device
		if(isIOSDevice(appiumCapabilities.getOsPlatform())){
			capabilities.setCapability("platformVersion", appiumCapabilities.getOsVersion());
			capabilities.setCapability("deviceName", appiumCapabilities.getDeviceName());
			capabilities.setCapability("bundleId", bundleId);
			driver = new IOSDriver<IOSElement>(url, capabilities);
			
			flagIOSDriver = true;
		}
		
		//For android device
		if(isAndroidDevice(appiumCapabilities.getOsPlatform())){
			flagIOSDriver = false;
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, appiumCapabilities.getOsVersion());
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, appiumCapabilities.getDeviceName());
			capabilities.setCapability(MobileCapabilityType.UDID, appiumCapabilities.getUdid());
			capabilities.setCapability("orientation", "PORTRAIT");	
			
			int timeOut = Integer.valueOf(Config.getConfigProperties(ConfigVar.APPIUM_SERVER_TIMEOUT));
			
			if(bundleId.equalsIgnoreCase(CHROME_BROWSER)){
				//Open CHROME browser on android
				capabilities.setCapability("autoAcceptAlerts", true);
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, CHROME_BROWSER);
			}else{
				//Launch application with bundleId and appActivity
				capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, bundleId);
				capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, appActivity);
				
				//Set Application Wait Activity if appActivityWait is NOT NULL 
				if(appActivityWait != null){
					capabilities.setCapability(MobileCapabilityType.APP_WAIT_ACTIVITY, appActivityWait);
				}
				
				capabilities.setCapability("newCommandTimeout", timeOut);
			}
			
			//Start appium driver
			driver = new AndroidDriver<WebElement>(url, capabilities);
			
			driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		}
		return driver;
	}
	
	/**
	 * Install application
	 * @param idDevice
	 * @param app
	 * @throws Exception 
	 */
	public static AppiumDriver<?> installApplication(String idDevice, String app) throws Exception{
		//Set application name
		appName = (new File(app)).getName();
		
		//Get information device by device id
		if(appiumCapabilities == null)
			getCapabilities(idDevice);
		
		//Throw exception when device information is NULL
		if(appiumCapabilities == null){
			throw new DeviceNotFoundException(idDevice);
		}
		
		URL url = new URL(appiumCapabilities.getUrlHub());
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		//For iOS device
		if(isIOSDevice(idDevice)){
			
			//Installation .ipa file
			capabilities.setCapability("platformVersion", appiumCapabilities.getOsVersion());
			capabilities.setCapability("deviceName", appiumCapabilities.getDeviceName());
			capabilities.setCapability("app", app);
			driver = new IOSDriver<IOSElement>(url, capabilities);
			
			flagIOSDriver = true;
		}
		
		//For android device
		if(isAndroidDevice(idDevice)){
			flagIOSDriver = false;
			
		}
		return driver;
	}
	
	/**
	 * Open URL on browser
	 * @param url
	 */
	public static void openUrl(String url){
		driver.get(url);
	}

	/**
	 * Reader object capabilities from excel file
	 * @param idDevice
	 * @return
	 * @throws Exception 
	 */
	private static void getCapabilities(String idDevice) throws Exception {
		
		//Get path file devices excel file
		String dataFolder = Config.getConfigProperties(ConfigVar.TEST_DATA_FOLDER);
		String deviceData = Config.getConfigProperties(ConfigVar.DEVICE_DATA_FILE);
		String pathFileDeviceData = dataFolder + "\\" + deviceData;
		BufferedReader br = null;
		String line = "";
		 String cvsSplitBy = ",";
		 try {

	            br = new BufferedReader(new FileReader(pathFileDeviceData));
	            while ((line = br.readLine()) != null) {
	            		
	                // use comma as separator
	                String[] data = line.split(cvsSplitBy);
	                String device = data[0];
	                if(device != null && device.equalsIgnoreCase(idDevice)){
	                	appiumCapabilities = new AppiumCapabilities();
	                	appiumCapabilities.setDeviceName(device);
	                	appiumCapabilities.setOsPlatform(data[1]);
	                	appiumCapabilities.setUdid(data[2]);
	                	appiumCapabilities.setOsVersion(data[3]);
	                	appiumCapabilities.setUrlHub(data[5]);
	                	appiumCapabilities.setUserName(data[6]);
	                	appiumCapabilities.setPassword(data[7]);
	                	break;
	                }
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		
		if(appiumCapabilities == null){
			throw new DeviceNotFoundException(idDevice);
		}
	}
	
	/**
	 * Quite connection appium 
	 */
	public static void closeConnection(){
		try {
			if(driver != null){
//				try {
//					driver.closeApp();
//				} catch (Exception e) {
//				}
				
				driver.quit();
			}
		} catch (Exception e) {
//			Log.error(e.getMessage());
		}
	}
	
	/**
	 * Take screen shot device
	 * @param fileName
	 * @throws IOException
	 */
	public static void getScreenshot(String fileName) throws IOException{
		if(driver != null){
			File scrFile  = driver.getScreenshotAs(OutputType.FILE);
			File desFile = new File(fileName);
			FileUtils.copyFile(scrFile, desFile);
		}
	}
	
	/**
	 * Get log device
	 */
	public static void createLogDevice(String logName){
		if(flagIOSDriver){
			
		}else{
			if(driver != null){
				List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
			    File logFile = new File(logName);
				try {
					PrintWriter log_file_writer = new PrintWriter(logFile);
					for (LogEntry logEntry : logEntries) {
				    	if(logEntry.getMessage().charAt(0) == 'E')
				    	  log_file_writer.println(logEntry);
					}
				  
				    log_file_writer.flush();
				    log_file_writer.close();
				} catch (FileNotFoundException e) {
					Log.error(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Un-install mobile application
	 * @param bundleId
	 */
	public static void uninstall(String bundleId){
		//if(!flagIOSDriver){
			driver.removeApp(bundleId);
		//}
	}
	
	/**
	 * Check application is installed
	 * @param bundleId
	 * @return
	 * @throws IOException 
	 */
	public static boolean isInstalled(String bundleId) throws Exception{
		//If running on Android device
		if(!flagIOSDriver){
			return App.getDriver().isAppInstalled(bundleId);
		}
		//For iOS device
		//String[] command = new String[] {IDEVICEINSTALLER, "-U", appiumCapabilities.getUdid(), "-l"}; ;
		String command = String.format("%s -U %s -l",IDEVICEINSTALLER, appiumCapabilities.getUdid());
		
		return executeIDeviceInstallerCommand(command).contains(bundleId);
	}
	
	/**
	 * Execute ideviceinstaller command on osx platform
	 * @param command
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("resource")
	public static String executeIDeviceInstallerCommand(String command) throws IOException{
		String urlStr = appiumCapabilities.getUrlHub();
		
		URL url = new URL(urlStr);
		String localIpaddress = InetAddress.getLocalHost().getHostAddress();
		String string = "";
		if(url.getHost().equals(localIpaddress)){
			Process p = Runtime.getRuntime().exec(command);
			InputStream is = p.getInputStream();
			Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
	        string = scanner.hasNext() ? scanner.next() : "";
	        scanner.close();
			is.close();
		}else{
			try {
				//Using SSH to run unix command
				Connection connection = new Connection(url.getHost());
				connection.connect();
				
				String username = appiumCapabilities.getUserName();
				
				String password = appiumCapabilities.getPassword();
				connection.authenticateWithPassword(username, password);
				//Open connection
				Session session = connection.openSession();
				session.execCommand(command);
				
				StringBuilder sb = new StringBuilder();
				InputStream stdout = new StreamGobbler(session.getStdout());
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
				String line = br.readLine();
				while (line != null) {
					Log.debug(line);
					sb.append(line + "\n");
					line = br.readLine();
				}
				br.close();
				stdout.close();
				session.close();
				connection.close();
				
				string = sb.toString();
			} catch (Exception e) {
				Log.error(e.getMessage());
			}
			
		}
		return string;
	}
	
	
	/**
	 * Install Application
	 * @param appPath
	 */
	public static void installApp(String appPath){
		App.getDriver().installApp(appPath);
	}
	
	/**
	 * Remove Application
	 * @param bundleId
	 */
	public static void removeApp(String bundleId){
		App.getDriver().removeApp(bundleId);
	}
	
	/**
	 * Switch context between webview and native
	 * @param context
	 */
	public static void switchContext(String context){
		if(context.equalsIgnoreCase(NATIVE_CONTEXT)){
			driver.context(NATIVE_CONTEXT);
		}else{
			Set<String> contextNames = driver.getContextHandles();
			for (String contextName : contextNames) {
				if (contextName.contains("WEB_VIEW")) {
					driver.context(contextName);
					break;
				}
			}
		}
	}
	
	/**
	 * Get version application is installed
	 * @param bundId
	 * @return
	 * @throws IOException 
	 * @throws ExecuteException 
	 */
	public static String getVersionApp(String bundId) throws ExecuteException, IOException{
		String version = null;
		if(flagIOSDriver){
			
		}else{
			String line = String.format("adb -s %s shell dumpsys package %s", appiumCapabilities.getUdid(), bundId);
			CommandLine command = CommandLine.parse(line);
			DefaultExecutor executor = new DefaultExecutor();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
			
			executor.setStreamHandler(streamHandler);
			executor.execute(command);
			
			String result = outputStream.toString();
			int indexOfVersioName = result.indexOf(VERSION_NAME);
			if(indexOfVersioName != -1){
				String temp = result.substring(indexOfVersioName + VERSION_NAME.length() + 1);
				version = temp.substring(0, temp.indexOf("\n")).trim();
			}
		}
		
		return version;
	}
	
	/**
	 * Check application will be installed
	 * @param bundleId
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static boolean waitForAppInstalled(String bundleId, float timeOut) throws Exception{
		long currentTime = System.currentTimeMillis();
		
		long maxTime = (long) (currentTime + timeOut * 60 * 1000);
		while (currentTime <= maxTime) {
			if(App.isInstalled(bundleId)){
				return true;
			}
			currentTime = System.currentTimeMillis();
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static final void startActivty(String bundleId, String activty){
		((AndroidDriver<WebElement>) driver).startActivity(bundleId, activty);
	}
	
	@SuppressWarnings("unchecked")
	public static final String getCurrentActity(){
		String activity = null;
		try {
			activity = ((AndroidDriver<WebElement>) driver).currentActivity();
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
		return activity;
	}
}
