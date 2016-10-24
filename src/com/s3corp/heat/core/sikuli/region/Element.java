package com.s3corp.heat.core.sikuli.region;

import org.sikuli.api.robot.Key;
import org.sikuli.script.FindFailed;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.s3corp.heat.core.sikuli.base.Application;

public class Element {

	private static Screen screen = Application.getScreen();
	
	/**
	 * Flag send action key or send action text
	 */
	public static final String FLAG_KEY = "<#Key>";
	
	/**
	 * Wait for region
	 * @param pattern
	 * @param timeOut
	 * @throws FindFailed
	 */
	public static void waitFor(String pattern, double timeOut) throws FindFailed{
		screen.wait(pattern, timeOut);
	}
	
	/**
	 * Send text action
	 * @param text
	 */
	public static void sendTextAction(String text){
		screen.type(text);
	}
	
	/**
	 * Send key action
	 * @param key
	 * @param count
	 */
	public static void sendKeyAction(String key, int count){
		key = key.replace(FLAG_KEY, "").trim();
		switch (key) {
			case KeyInput.ALT:
				sendRepeatKey(Key.ALT, count);
				break;
			case KeyInput.WINDOWS:
				sendRepeatKey(Key.WIN, count);
				break;
			case KeyInput.CTRL:
				sendRepeatKey(Key.CTRL, count);
				break;
			case KeyInput.SHIFT:
				sendRepeatKey(Key.SHIFT, count);
				break;
			case KeyInput.CAPS_LOCK:
				sendRepeatKey(Key.CAPS_LOCK, count);
				break;
			case KeyInput.TAB:
				sendRepeatKey(Key.TAB, count);
				break;
			case KeyInput.BACKSPACE:
				sendRepeatKey(Key.BACKSPACE, count);
				break;
			case KeyInput.ENTER:
				sendRepeatKey(Key.ENTER, count);
				break;
			case KeyInput.SPACE:
				sendRepeatKey(Key.SPACE, count);
				break;
			case KeyInput.RIGHT:
				sendRepeatKey(Key.RIGHT, count);
				break;
			case KeyInput.UP:
				sendRepeatKey(Key.UP, count);
				break;
			case KeyInput.LEFT:
				sendRepeatKey(Key.LEFT, count);
				break;
			case KeyInput.DOWN:
				sendRepeatKey(Key.DOWN, count);
				break;
			case KeyInput.HOME:
				sendRepeatKey(Key.HOME, count);
				break;
			default:
				break;
		}
	}

	/**
	 * Send repeat action key
	 * @param key
	 * @param count
	 */
	private static void sendRepeatKey(String key, int count) {
		for (int i = 0; i < count; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendTextAction(key);
		}
	}
	
	/**
	 * Event click at region
	 * @param img
	 * @throws FindFailed
	 */
	public static void click(String img) throws FindFailed{
		Pattern p = createPattern(img);
		screen.click(p);
	}
	
	/**
	 * Create new region
	 * @param img
	 * @return
	 */
	public static Pattern createPattern(String img){
		return new Pattern(img);
	}
	
	/**
	 * Send combination key
	 * @param key
	 */
	public static void sendKeyCombine(String key){
		
		//Replace <#Key> string
		key = key.replace(FLAG_KEY, "").trim();
		
		//Replace "+" character
		key = key.replaceAll("\\+", " ");
		
		String[] keys = key.split(" ");
		
		if(keys.length == 2){
			String key1 = keys[0];
			String key2 = keys[1];
			
			switch (key1) {
				case KeyInput.CTRL:
					screen.type(key2, KeyModifier.CTRL);
					break;
				case KeyInput.SHIFT:
					screen.type(KeyModifier.SHIFT + key2);
					break;
				case KeyInput.ALT:
					if(key2.equalsIgnoreCase(KeyInput.SPACE)){
						key2 = " ";
					}else{
						key2 = KeyInput.getValueOfKey(key2);
					}
					screen.type(key2, KeyModifier.ALT);
					break;
				default:
					break;
			}
		}else if(keys.length == 3){
			String key1 = keys[0];
			String key2 = keys[1];
			String key3 = keys[2];
			screen.type(KeyInput.getValueKeyModifier(key2) + key3, KeyInput.getValueKeyModifier(key1));
		}
	}
	
	/**
	 * Double-click at a locator
	 * @param pattern
	 * @throws FindFailed 
	 */
	public static void doubleClick(String pattern) throws FindFailed{
		Pattern p = createPattern(pattern);
		screen.doubleClick(p);
	}
	
	/**
	 * Drag and drop action
	 * @param des
	 * @param target
	 * @throws FindFailed 
	 */
	public static void drapDrop(String des, String target) throws FindFailed{
		screen.dragDrop(createPattern(des), createPattern(target));
	}
	
	/**
	 * Hover at locator
	 * @param pattern
	 * @throws FindFailed 
	 */
	public static void hover(String pattern) throws FindFailed{
		screen.hover(createPattern(pattern));
	}
	
	/**
	 * Paste text value
	 * @param text
	 */
	public static void paste(String text){
		screen.paste(text);
	}
	
	/**
	 * Paste text value at locator
	 * @param text
	 * @param pattern
	 * @throws FindFailed 
	 */
	public static void paste(String pattern, String text) throws FindFailed{
		screen.paste(createPattern(pattern), text);
	}
	
	/**
	 * Right click event
	 */
	public static void rightClick(){
		screen.rightClick();
	}
	
	/**
	 * Right click at locator
	 * @param pattern
	 * @throws FindFailed
	 */
	public static void rightClick(String pattern) throws FindFailed{
		screen.rightClick(createPattern(pattern));
	}
	
	/**
	 * Right click at point
	 * @param x
	 * @param y
	 * @throws FindFailed
	 */
	public static void rightClick(int x, int y) throws FindFailed{
		screen.rightClick(new Location(x, y));
	}
	
	/**
	 * Wait for locator is vanish
	 * @param p
	 * @param timeOut
	 */
	public static void waitForVanish(String p, long timeOut){
		screen.waitVanish(createPattern(p), timeOut);
	}
	
	/**
	 * Check Survival of image
	 * @param p
	 * @return
	 */
	public static boolean checkExist(String p){
		Match match = screen.exists(createPattern(p));
		return match != null;
	}
}
