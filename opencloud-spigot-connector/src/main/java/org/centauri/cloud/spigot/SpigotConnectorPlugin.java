package org.centauri.cloud.spigot;

import java.util.logging.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.centauri.cloud.opencloud.connector.netty.Client;
import org.centauri.cloud.spigot.netty.NetworkHandler;

public class SpigotConnectorPlugin extends JavaPlugin{
	
	@Getter private static SpigotConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	@Getter private Client client;
	
	@Override
	public void onLoad() {
		SpigotConnectorPlugin.instance = this;
		SpigotConnectorPlugin.pluginLogger = this.getLogger();
		
		this.client = new Client(new NetworkHandler(), "127.0.0.1", 8012);
		
		getPluginLogger().info("Loaded CentauriCloud spigot connector.");
	}
	
	@Override
	public void onEnable() {
		getPluginLogger().info("Enabled CentauriCloud spigot connector.");
	}
	
	@Override
	public void onDisable() {
		getPluginLogger().info("Disabled CentauriCloud spigot connector.");
	}
}
