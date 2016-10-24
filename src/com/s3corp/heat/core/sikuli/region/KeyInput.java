package com.s3corp.heat.core.sikuli.region;

import org.sikuli.api.robot.Key;
import org.sikuli.script.KeyModifier;

public class KeyInput {
	
	public static final String ALT = "ALT";
	
	public static final String WINDOWS = "WINDOWS";
	
	public static final String CTRL = "CTRL";
	
	public static final String SHIFT = "SHIFT";
	
	public static final String CAPS_LOCK = "CAPS_LOCK";
	
	public static final String TAB = "TAB";
	
	public static final String BACKSPACE = "BACKSPACE";
	
	public static final String ENTER = "ENTER";
	
	public static final String RIGHT = "RIGHT";
	
	public static final String UP = "UP";
	
	public static final String LEFT = "LEFT";
	
	public static final String DOWN = "DOWN";
	
	public static final String SPACE = "SPACE";
	
	public static final String HOME = "HOME";
	
	public static final String F1 = "F1";
	public static final String F2 = "F2";
	public static final String F3 = "F3";
	public static final String F4 = "F4";
	public static final String F5 = "F5";
	public static final String F6 = "F6";
	public static final String F7 = "F7";
	public static final String F8 = "F8";
	public static final String F9 = "F9";
	public static final String F10 = "F10";
	public static final String F11 = "F11";
	public static final String F12 = "F12";
	
	/**
	 * Get value of key modifier
	 * @param key
	 * @return
	 */
	public static int getValueKeyModifier(String key){
		int i = 0;
		switch (key) {
			case SHIFT:
				i = KeyModifier.SHIFT;
				break;
			case CTRL:
				i = KeyModifier.CTRL;
				break;
			case ALT:
				i = KeyModifier.ALT;
				break;
			default:
				break;
		}
		
		return i;
	}
	
	/**
	 * Get value of key
	 * @param key
	 * @return
	 */
	public static String getValueOfKey(String key){
		String result = "";
		switch (key) {
			case F1:
				result = Key.F1; 
				break;
			case F2:
				result = Key.F2; 
				break;
			case F3:
				result = Key.F3; 
				break;
			case F4:
				result = Key.F4; 
				break;
			case F5:
				result = Key.F5; 
				break;
			case F6:
				result = Key.F6; 
				break;
			case F7:
				result = Key.F7; 
				break;
			case F8:
				result = Key.F8; 
				break;
			case F9:
				result = Key.F9; 
				break;
			case F10:
				result = Key.F10; 
				break;
			case F11:
				result = Key.F11; 
				break;
			case F12:
				result = Key.F12; 
				break;
			default:
				break;
		}
		return result;
	}
}
