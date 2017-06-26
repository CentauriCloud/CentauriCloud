package org.centauri.cloud.spigot.config;

import lombok.Getter;
import org.centauri.cloud.common.network.config.TemplateConfig;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

import java.io.File;
import java.util.logging.Level;

public class CloudConfiguration {

	@Getter private String hostname;
	@Getter private int port;
	@Getter private String prefix;

	public CloudConfiguration() {
		try {
			File dir = new File(".").getCanonicalFile();
			TemplateConfig config = new TemplateConfig(dir);
			this.hostname = config.getString("master.host");
			this.port = config.getInt("master.port");
			this.prefix = dir.getName().split("-")[0];
		} catch (Exception ex) {
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "Cannot load config", ex);
		}
	}
}