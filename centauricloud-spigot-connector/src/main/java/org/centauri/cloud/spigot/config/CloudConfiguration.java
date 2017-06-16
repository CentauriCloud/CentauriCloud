package org.centauri.cloud.spigot.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

public class CloudConfiguration {
	
	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;
	
	public CloudConfiguration(String path) {
		try {
			Properties config = new Properties();
			FileInputStream fin = new FileInputStream(path);
			try {
				config.load(fin);
			} finally {
				fin.close();
			}
			
			this.hostname = config.getProperty("hostname");
			this.port = Integer.valueOf(config.getProperty("port"));
			this.prefix = config.getProperty("prefix");
		} catch (Exception ex) {
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "Cannot load config", ex);
		}
	}
}