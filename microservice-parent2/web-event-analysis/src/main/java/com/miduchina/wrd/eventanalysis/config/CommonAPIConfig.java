package com.miduchina.wrd.eventanalysis.config;

import java.io.Serializable;
import java.util.Properties;

public class CommonAPIConfig implements Serializable {
	private static final long serialVersionUID = -1152960490461535047L;

	private static Properties props = new Properties();

	public CommonAPIConfig() {
	}

	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("common-api-config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return props.getProperty(key);
	}

	public static void updateProperties(String key, String value) {
		props.setProperty(key, value);
	}
}
