package com.s3corp.heat.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static String TEST_CONFIG_FILE = "test-config/config.cfg";

	private static Properties properties = new Properties();

	static {
		getProperties();
	}

	private static void getProperties() {
		try {
			InputStream is = new FileInputStream(new File(TEST_CONFIG_FILE));
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get value of configuration file
	 * @param keyWord
	 * @return
	 */
	public static String getConfigProperties(String keyWord) {
		return properties.getProperty(keyWord);
	}
}
