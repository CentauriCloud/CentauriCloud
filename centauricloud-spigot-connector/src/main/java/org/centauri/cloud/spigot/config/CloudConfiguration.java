package org.centauri.cloud.spigot.config;

import java.io.File;
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
			YamlConfiguration config = new YamlConfiguration();
			config.load(new File(path));

			if (!(config.isSet("hostname")
					&& config.isSet("port")
					&& config.isSet("prefix"))) {
				SpigotConnectorPlugin.getInstance().saveDefaultConfig();
			}
			this.hostname = config.getString("hostname");
			this.port = config.getInt("port");
			this.prefix = config.getString("prefix");
		} catch (Exception ex) {
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "Cannot load config", ex);
		}
	}
}