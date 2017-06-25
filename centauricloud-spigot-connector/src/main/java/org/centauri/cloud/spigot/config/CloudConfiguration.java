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
			String[] path = new File("").getAbsolutePath().split("\\\\");
			this.prefix = path[path.length - 1].split("-")[0];
			System.out.println("hostname" + hostname);
			System.out.println("port" + port);
			System.out.println("prefix" + prefix);
		} catch (Exception ex) {
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "Cannot load config", ex);
		}
	}
}