package org.centauri.cloud.daemon.config;

import lombok.Getter;

import java.util.Properties;

public class CloudConfiguration {

	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;

	public CloudConfiguration(Properties properties) {

		this.hostname = (String) properties.get("hostname");
		this.port = Integer.valueOf((String) properties.get("port"));
		this.prefix = (String) properties.get("prefix");
	}
}
