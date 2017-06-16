package org.centauri.cloud.bungee.config;

import java.io.File;
import java.util.logging.Level;
import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.centauri.cloud.bungee.BungeeConnectorPlugin;

public class CloudConfiguration {
	
	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;
	
	public CloudConfiguration(String path){
		try {
		Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(path));
		
		this.hostname = config.getString("hostname");
		this.port = config.getInt("port");
		this.prefix = config.getString("prefix");
		} catch(Exception ex) {
			BungeeConnectorPlugin.getPluginLogger().log(Level.WARNING, "Cannot load config", ex);
		}
	}
}
