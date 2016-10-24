package com.s3corp.heat.core.sikuli.base;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

import com.s3corp.heat.config.Config;
import com.s3corp.heat.config.ConfigVar;
import com.s3corp.heat.core.sikuli.os.Platform;
import com.s3corp.heat.log.Log;

public class Application {

	private static App app;
	private static Screen screen;
	
	static{
		//Disable log Sikuli
		Settings.ActionLogs = false;
		
		//Set image path
		String imagePath = Config.getConfigProperties(ConfigVar.SIKULI_IMAGE_PATH);
		ImagePath.add(imagePath);
	}
	public static Screen getScreen(){
		if(screen == null)
			screen = new Screen();
		return screen;
	}
	
	/**
	 * Open application
	 * @param appName
	 */
	public static void  openApp(String appName) throws Exception{
		app = App.open(appName);
		if(screen != null)
			screen = new Screen();
	}
	
	/**
	 * Close application
	 */
	public static void closeApp(){
		if(app != null)
			app.close();
		if(screen != null)
			screen = null;
	}
	
	/**
	 * Close application
	 * @param appName
	 */
	public static void closeApp(String appName){
		App.close(appName);
		if(Platform.checkApplicationIsRunning(appName)){
			Log.debug("Kill process " + appName);
			Platform.killProcess(appName);
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get screenshot
	 * @param fileName
	 * @throws AWTException 
	 * @throws IOException 
	 */
	public static void getScreenshot(String fileName) throws AWTException, IOException{
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 Dimension screenSize = toolkit.getScreenSize();
		 Rectangle screenRect = new Rectangle(screenSize);
		 Robot robot = new Robot();
		 BufferedImage image = robot.createScreenCapture(screenRect);
		 ImageIO.write(image, "jpg", new File(fileName));
	}
}
