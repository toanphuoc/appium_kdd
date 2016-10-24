package com.s3corp.heat.core.appium.base;

public class AppiumCapabilities {

	private String osVersion;
	private String osPlatform;
	private String udid;
	private String deviceName;
	private String urlHub;
	private String userName;
	private String password;
	
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getOsPlatform() {
		return osPlatform;
	}
	public void setOsPlatform(String osPlatform) {
		this.osPlatform = osPlatform;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getUrlHub() {
		return urlHub;
	}
	public void setUrlHub(String urlHub) {
		this.urlHub = urlHub;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
