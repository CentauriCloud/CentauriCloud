package org.centauri.cloud.spigot.config;

import lombok.Getter;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

import java.io.File;
import java.util.logging.Level;

public class CloudConfiguration {

	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;

	public CloudConfiguration() {
		try {
			TemplateConfig config = new TemplateConfig();
			this.hostname = config.getString("master.host");
			this.port = config.getInt("master.port");
			this.prefix = new File(".").getCanonicalFile().getName().split("-")[0];
		} catch (Exception ex) {
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "Cannot load config", ex);
		}
	}
}