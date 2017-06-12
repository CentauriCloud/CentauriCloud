package org.centauri.cloud.bungee;

import java.util.logging.Logger;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeConnectorPlugin extends Plugin{
	
	@Getter private static BungeeConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	
	@Override
	public void onLoad() {
		BungeeConnectorPlugin.instance = this;
		BungeeConnectorPlugin.pluginLogger = this.getLogger();
		
		getPluginLogger().info("Loaded CentauriCloud bungee connector.");
	}
	
	@Override
	public void onEnable() {
		getPluginLogger().info("Enabled CentauriCloud bungee connector.");
	}
	
	@Override
	public void onDisable() {
		getPluginLogger().info("Disabled CentauriCloud bungee connector.");
	}
	
}
