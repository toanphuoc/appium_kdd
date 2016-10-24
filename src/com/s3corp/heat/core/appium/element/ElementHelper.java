package com.s3corp.heat.core.appium.element;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.s3corp.heat.core.appium.base.App;

import io.appium.java_client.MobileBy;

public class ElementHelper{

	/**
	 * Flag find element by text
	 */
	public static final String FLAG_TEXT = "text=";
	
	/**
	 * Flag find element by xPath
	 */
	public static final String FLAG_XPATH = "xpath=";
	
	/**
	 * Flag find element by id
	 */
	public static final String FLAG_ID = "id=";
	
	/**
	 * Flag find element by class 
	 */
	public static final String FLAG_CLASS = "class=";
	
	/**
	 * Flag find element by name
	 */
	public static final String FLAG_NAME = "name=";
	
	/**
	 * Flag find element by iOS UI automation
	 */
	public static final String FLAG_IOS = "ios=";
	
	/**
	 * Find all elements match as xPath
	 * @param keyFind
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<WebElement> findElements(String keyFind){
		By by = getBy(keyFind);
		return (List<WebElement>) App.getDriver().findElements(by);
	}
	
	/**
	 * Find IOS Element 
	 * @param keyFind
	 * @return
	 */
	public static WebElement findElement(String keyFind){
		By by =  getBy(keyFind);
		return App.getDriver().findElement(by);
	}
	
	/**
	 * Create By object by find key
	 * @param keyFind
	 * @return
	 */
	public static By getBy(String keyFind) throws WebDriverException{
		By by = null;
		
		if(keyFind.toLowerCase().startsWith(FLAG_TEXT.toLowerCase())){
			keyFind = keyFind.replace(FLAG_TEXT, "").trim();
			String xpath = "//text[@value='" + keyFind + "']";
			by = By.xpath(xpath);
		}
		
		if(keyFind.toLowerCase().startsWith(FLAG_ID.toLowerCase())){
			by = By.id(keyFind.replace(FLAG_ID, "").trim());
		}
		
		if(keyFind.toLowerCase().startsWith(FLAG_CLASS.toLowerCase())){
			by = By.className(keyFind.replace(FLAG_CLASS, "").trim());
		}
		
		if(keyFind.toLowerCase().startsWith(FLAG_XPATH.toLowerCase())){
			by = By.xpath(keyFind.replace(FLAG_XPATH, "").trim());
		}
		
		if(keyFind.toLowerCase().startsWith(FLAG_NAME.toLowerCase())){
			by = By.name(keyFind.replace(FLAG_NAME, "").trim());
		}
		
		if(keyFind.toLowerCase().startsWith(FLAG_IOS.toLowerCase())){
			by = MobileBy.IosUIAutomation(keyFind.replace(FLAG_IOS, "").trim());
		}
		
		
		return by;
	}
	
	/**
	 * Get center point
	 * @param ele
	 * @return
	 */
	public static Point getCenterPoint(WebElement ele){
		int startX = ele.getLocation().getX();
		int startY = ele.getLocation().getY();
		
		int width = ele.getSize().getWidth();
		int height = ele.getSize().getHeight();
		
		return new Point(startX + width / 2, startY + height / 2);
	}
}
