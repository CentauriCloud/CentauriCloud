package org.centauri.cloud.spigot;

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.centauri.cloud.common.network.packets.PacketCloseConnection;
import org.centauri.cloud.centauricloud.connector.netty.Client;
import org.centauri.cloud.spigot.config.CloudConfiguration;
import org.centauri.cloud.spigot.listener.PlayerListener;
import org.centauri.cloud.spigot.netty.NetworkHandler;

public class SpigotConnectorPlugin extends JavaPlugin{
	
	@Getter private static SpigotConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	@Getter private Client client;
	@Getter private CloudConfiguration cloudConfiguration;
	
	@Override
	public void onLoad() {
		SpigotConnectorPlugin.instance = this;
		SpigotConnectorPlugin.pluginLogger = this.getLogger();
		
		getPluginLogger().info("Loaded CentauriCloud spigot connector.");
		
	}
	
	@Override
	public void onEnable() {
		this.cloudConfiguration = new CloudConfiguration("centauricloud.properties");
		
		getPluginLogger().info(String.format("%s -> %s:%s", cloudConfiguration.getPrefix(), cloudConfiguration.getHostname(), cloudConfiguration.getPort()));
			
		new Thread(() -> {
			System.out.println("Try to start netty client...");
			
			this.client = new Client(new NetworkHandler(), cloudConfiguration.getHostname(), cloudConfiguration.getPort());
			try {
				this.client.start();
			} catch (Exception ex) {
				Logger.getLogger(SpigotConnectorPlugin.class.getName()).log(Level.SEVERE, null, ex);
			}
		}, "Netty-Thread").start();

		this.registerListener();
		
		getPluginLogger().info("Enabled CentauriCloud spigot connector.");
	}

	private void registerListener() {
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}
	
	@Override
	public void onDisable() {
		this.client.getChannel().writeAndFlush(new PacketCloseConnection());
		getPluginLogger().info("Disabled CentauriCloud spigot connector.");
	}
}
