package com.s3corp.heat.testng.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ActionKeyReader {

	private static Properties properties = new Properties();
	
	static{
		getProperties();
	}
	
	private static void getProperties(){
		try {
			String propFileName = "config/keyword_class_config.properties";
			InputStream is = ActionKeyReader.class.getClassLoader().getResourceAsStream(propFileName);
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getClassKeywordConfig(String keyWord){
		return properties.getProperty(keyWord);
	}
}
