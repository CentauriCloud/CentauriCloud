package org.centauri.cloud.bungee;

import java.util.logging.Logger;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import org.centauri.cloud.bungee.netty.NetworkHandler;
import org.centauri.cloud.opencloud.connector.netty.Client;

public class BungeeConnectorPlugin extends Plugin{
	
	@Getter private static BungeeConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	@Getter private Client client;
	
	@Override
	public void onLoad() {
		BungeeConnectorPlugin.instance = this;
		BungeeConnectorPlugin.pluginLogger = this.getLogger();
		
		new Thread(() -> {
 			this.client = new Client(new NetworkHandler(), "127.0.0.1", 8012);
 		}, "Netty-Thread").start();
		
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
