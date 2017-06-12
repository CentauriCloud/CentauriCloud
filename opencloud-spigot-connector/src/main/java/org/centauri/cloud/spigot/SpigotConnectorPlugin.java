package org.centauri.cloud.spigot;

import java.util.logging.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotConnectorPlugin extends JavaPlugin{
	
	@Getter private static SpigotConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	
	@Override
	public void onLoad() {
		SpigotConnectorPlugin.instance = this;
		SpigotConnectorPlugin.pluginLogger = this.getLogger();
		
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
