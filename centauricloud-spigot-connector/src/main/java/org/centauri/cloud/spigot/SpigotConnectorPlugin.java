package org.centauri.cloud.spigot;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.centauri.cloud.centauricloud.connector.netty.Client;
import org.centauri.cloud.centauricloud.connector.netty.PacketLoader;
import org.centauri.cloud.common.network.packets.PacketCloseConnection;
import org.centauri.cloud.common.network.util.PacketHandler;
import org.centauri.cloud.spigot.config.CloudConfiguration;
import org.centauri.cloud.spigot.netty.NetworkHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpigotConnectorPlugin extends JavaPlugin {

	@Getter private static SpigotConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	@Getter private Client client;
	@Getter private CloudConfiguration cloudConfiguration;
	@Getter private Set<PacketHandler> packetHandlers = new HashSet<>();

	@Override
	public void onLoad() {
		SpigotConnectorPlugin.instance = this;
		SpigotConnectorPlugin.pluginLogger = this.getLogger();

		getPluginLogger().info("Loaded CentauriCloud spigot connector.");

	}

	@Override
	public void onEnable() {
		this.cloudConfiguration = new CloudConfiguration();

		new PacketLoader().readFile(this.getLogger());

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

		getPluginLogger().info("Enabled CentauriCloud spigot connector.");
	}

	@Override
	public void onDisable() {
		this.client.getChannel().writeAndFlush(new PacketCloseConnection());
		getPluginLogger().info("Disabled CentauriCloud spigot connector.");
	}

	public static void registerPlugin(JavaPlugin plugin) {
		registerPlugin(plugin, null);
	}

	public static void registerPlugin(JavaPlugin plugin, PacketHandler packetHandler) {
		SpigotConnectorPlugin connector = SpigotConnectorPlugin.getInstance();
		if(connector == null) {
			throw new IllegalStateException("SpigotConnectorPlugin is not loaded successfully!");
		}
		
		if(packetHandler != null)
			connector.packetHandlers.add(packetHandler);

		new PacketLoader().readFile(connector.getLogger());
	}

}
