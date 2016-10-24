package com.s3corp.heat.core.appium.element;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.exception.ElementNotFoundException;

public class ElementAction {

	/**
	 * Appium handle send key
	 * @param keyFind
	 * @param key
	 */
	public static void sendKeys(String keyFind, String key){
		
		WebElement ele = ElementHelper.findElement(keyFind);
		ele.sendKeys(key);
	}
	
	/**
	 * Appium handle click
	 * @param keyFind
	 * @throws ElementNotFoundException 
	 */
	public static void click(String keyFind){
		WebElement ele = ElementHelper.findElement(keyFind);
		ele.click();
	}
	
	/**
	 * Wait for element to Clickable
	 * @param key
	 * @param timeOut
	 */
	public static void waitFor(String key, long timeOut){
		WebDriverWait wait = new WebDriverWait(App.getDriver(), timeOut);
		By by = ElementHelper.getBy(key);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	/**
	 * Wait for text
	 * @param key
	 * @param text
	 * @param timeout
	 */
	public static void waitForText(String key, String text, long timeout){
		WebDriverWait wait = new WebDriverWait(App.getDriver(), timeout);
		WebElement ele = ElementHelper.findElement(key);
		wait.until(ExpectedConditions.textToBePresentInElement(ele, text));
	}
	
	/**
	 * Appium handle tap
	 * @param iosUIAutomation
	 */
	public static void tap(String iosUIAutomation){
		WebElement ele = ElementHelper.findElement(iosUIAutomation);
		App.getDriver().tap(1, ele, 1);
	}
	
	/**
	 * Get text value of element
	 * @param eleText
	 * @return
	 */
	public static String getTextElement(String eleText){
		WebElement ele = ElementHelper.findElement(eleText);
		return ele.getText();
	}
	
	/**
	 * Get count of element
	 * @param ele
	 * @return
	 */
	public static int countElements(String ele){
		return ElementHelper.findElements(ele).size();
	}
	
	/**
	 * Check element is displayed
	 * @param ele
	 * @return
	 */
	public static boolean isDisplay(String ele){
		return ElementHelper.findElement(ele).isDisplayed();
	}
	
	/**
	 * Wait for at element is vanish
	 * @param ele
	 * @param timeOut
	 */
	public static boolean waitForVanish(String eleStr, long timeOut){
		
		boolean result = false;
		long timeout = System.currentTimeMillis() + timeOut * 1000;
		while((System.currentTimeMillis() <= timeout)){
			if(countElements(eleStr) == 0){
				result = true;
				break;
			}
			
		}
		return result;
	}
	
	/**
	 * Swipe action from 2 web element
	 * @param ele1
	 * @param ele2
	 * @param duration
	 */
	public static void swipe(String ele1, String ele2, int duration){
		WebElement webElement1 = ElementHelper.findElement(ele1);
		WebElement webElement2 = ElementHelper.findElement(ele2);
		
		Point centerA = ElementHelper.getCenterPoint(webElement1);
		Point centerB = ElementHelper.getCenterPoint(webElement2);
		
		swipe(centerA.getX(), centerA.getY(), centerB.getX(), centerB.getY(), duration);
	}
	
	
	/**
	 * Swipe action
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param duration
	 */
	public static void swipe(int startX, int startY, int endX, int endY, int duration){
		App.getDriver().swipe(startX, startY, endY, endX, duration);
	}
	
	/**
	 * Select value in select element
	 * @param ele
	 * @param value
	 */
	public static void selectOption(String ele, String value){
		Select select  = new Select(ElementHelper.findElement(ele));
		select.selectByVisibleText(value);
	}
	
	/**
	 * Select index in select element
	 * @param ele
	 * @param index
	 */
	public static void selectOption(String ele, int index){
		Select select  = new Select(ElementHelper.findElement(ele));
		select.selectByIndex(index);
	}
	
	/**
	 * Scroll to text
	 * @param text
	 */
	public static void scrollTo(String text) throws Exception{
//		App.getDriver().scrollTo(text);
		App.getDriver().scrollToExact(text);
	}
}
