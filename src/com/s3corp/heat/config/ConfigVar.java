package com.s3corp.heat.config;

public class ConfigVar {

	/**
	 * Configuration TestNG output folder
	 */
	public static final String REPORT_OUTPUT_DIR = "test_output";
	
	/**
	 * Test data folder
	 */
	public static final String TEST_DATA_FOLDER = "folder_data";
	
	/**
	 * Test data file
	 */
	public static final String TEST_DATA_FILE = "data_file";
	
	/**
	 * Devices data test file
	 */
	public static final String DEVICE_DATA_FILE = "devices_data";
	
	/**
	 * iOS Sheet in Excel file
	 */
	public static final String IOS_SHEET = "iOS";
	
	
	/**
	 * Android Sheet in Excel file
	 */
	public static final String ANDROID_SHEET = "Android";
	
	/**
	 * Row column device name
	 */
	public static final int ROW_DEVICE_NAME = 0;
	
	/**
	 * Row column device udid
	 */
	public static final int ROW_DEVICE_UDID = 1;
	
	/**
	 * Row column device platform version
	 */
	public static final int ROW_DEVICE_VERSION = 2;
	
	/**
	 * Row column device platform serial number
	 */
	public static final int ROW_DEVICE_SERIAL_NUMBER = 3;
	
	/**
	 * Row column device platform Appium hub URL
	 */
	public static final int ROW_DEVICE_URL_APPIUM = 4;
	
	/**
	 * Row column device platform username appium server
	 */
	public static final int ROW_USERNAME_APPIUM_SERVER = 5;
	
	/**
	 * Row column device platform password appium server
	 */
	public static final int ROW_PASSWORD_APPIUM_SERVER = 6;
	
	/**
	 * Screen short device
	 */
	public static final String ANABLE_CAPTURE_SCREENSHOT = "enable_capture_screenshot";
	
	/**
	 * Log file device
	 */
	public static final String ENABLE_LOG_FILE_DEVICE = "enable_log_file_device";
	
	/**
	 * Flag enable send mail automation
	 */
	public static final String ENABLE_SEND_MAIL_AUTO = "enable_send_mail_automation";

	/**
	 * Set image path of Sikuli
	 */
	public static final String SIKULI_IMAGE_PATH = "image_path";
	
	/**
	 * Appium Server timeout
	 */
	public static final String APPIUM_SERVER_TIMEOUT = "appium_server_timeout";
	
}
