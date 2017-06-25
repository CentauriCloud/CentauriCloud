package org.centauri.cloud.bungee.util;

import net.md_5.bungee.api.config.ServerInfo;
import org.centauri.cloud.bungee.BungeeConnectorPlugin;

import java.net.InetSocketAddress;

public class ServerUtil {

	public static void addServer(String name, InetSocketAddress address, String motd, boolean restricted) {
		ServerInfo serverInfo = BungeeConnectorPlugin.getInstance().getProxy().constructServerInfo(name, address, motd, restricted);
		BungeeConnectorPlugin.getInstance().getProxy().getServers().put(name, serverInfo);
	}

	public static void removeServer(String name) {
		BungeeConnectorPlugin.getInstance().getProxy().getServers().remove(name);
	}
}