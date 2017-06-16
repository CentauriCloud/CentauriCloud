package org.centauri.cloud.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.centauri.cloud.bungee.config.CloudConfiguration;
import org.centauri.cloud.bungee.listener.PlayerListener;
import org.centauri.cloud.bungee.netty.NetworkHandler;
import org.centauri.cloud.bungee.server.ServerManager;
import org.centauri.cloud.centauricloud.connector.netty.Client;
import org.centauri.cloud.common.network.packets.PacketCloseConnection;

public class BungeeConnectorPlugin extends Plugin{
	
	@Getter private static BungeeConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	@Getter private Client client;
	@Getter private CloudConfiguration cloudConfiguration;
	@Getter private ServerManager serverManager;
	
	@Override
	public void onLoad() {
		BungeeConnectorPlugin.instance = this;
		BungeeConnectorPlugin.pluginLogger = this.getLogger();
				
		getPluginLogger().info("Loaded CentauriCloud bungee connector.");
	}
	
	@Override
	public void onEnable() {		
		getPluginLogger().info(String.format("%s -> %s:%s", cloudConfiguration.getPrefix(), cloudConfiguration.getHostname(), cloudConfiguration.getPort()));
		
		this.cloudConfiguration = new CloudConfiguration("centauricloud.properties");
		
		this.serverManager = new ServerManager();
		
		this.getProxy().getPluginManager().registerListener(this, new PlayerListener());
		
		new Thread(() -> {
			System.out.println("Try to start netty client...");

			this.client = new Client(new NetworkHandler(), cloudConfiguration.getHostname(), cloudConfiguration.getPort());
			try {
				this.client.start();
			} catch (Exception ex) {
				Logger.getLogger(BungeeConnectorPlugin.class.getName()).log(Level.SEVERE, null, ex);
			}
		}, "Netty-Thread").start();
		
		getPluginLogger().info("Enabled CentauriCloud bungee connector.");
	}
	
	@Override
	public void onDisable() {
		this.client.getChannel().writeAndFlush(new PacketCloseConnection());
		getPluginLogger().info("Disabled CentauriCloud bungee connector.");
	}

}
