package org.centauri.cloud.cloud.config;

public class Config {

	private PropertyManager manager = PropertyManager.getInstance();

	protected String get(String key) {
		return manager.getProperties().getProperty(key);
	}

}
