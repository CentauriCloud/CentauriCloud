package org.centauri.cloud.spigot.config;

import java.io.File;
import java.util.logging.Level;
import lombok.Getter;
import org.centauri.cloud.common.network.config.TemplateConfig;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

public class CloudConfiguration {
	
	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;
	
	public CloudConfiguration(String path) {
		try {
			TemplateConfig config = new TemplateConfig(new File("."));
			
			this.hostname = config.getString("hostname");
			this.port = config.getInt("port");
			this.prefix = new File(".").getName();
		} catch (Exception ex) {
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "Cannot load config", ex);
		}
	}
}