package org.centauri.cloud.bungee.config;

import lombok.Getter;
import net.md_5.bungee.config.Configuration;

public class CloudConfiguration {
	
	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;
	
	public CloudConfiguration(Configuration config){
		
		this.hostname = config.getString("hostname");
		this.port = config.getInt("port");
		this.prefix = config.getString("prefix");
	}
}
