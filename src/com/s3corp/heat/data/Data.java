package com.s3corp.heat.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.s3corp.heat.config.Config;
import com.s3corp.heat.config.ConfigVar;

public class Data {

	private static Properties properties = new Properties();

	static {
		String testDataFile = Config.getConfigProperties(ConfigVar.TEST_DATA_FOLDER) + "\\" + Config.getConfigProperties(ConfigVar.TEST_DATA_FILE);
		getProperties(testDataFile);
	}

	private static void getProperties(String testDataFile) {
		try {
			InputStream is = new FileInputStream(new File(testDataFile));
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
