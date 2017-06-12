package org.centauri.cloud.spigot.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

public class CloudConfiguration {
	
	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;
	
	public CloudConfiguration(FileConfiguration config){
		
		if(!(config.isSet("hostname")
				&& config.isSet("port")
				&& config.isSet("prefix"))){
			SpigotConnectorPlugin.getInstance().saveDefaultConfig();
		}
		this.hostname = config.getString("hostname");
		this.port = config.getInt("port");
		this.prefix = config.getString("prefix");
	}
}