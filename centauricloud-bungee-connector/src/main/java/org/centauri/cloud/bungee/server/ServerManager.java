package org.centauri.cloud.bungee.server;

import net.md_5.bungee.api.config.ServerInfo;
import org.centauri.cloud.bungee.BungeeConnectorPlugin;

import java.util.Set;
import java.util.stream.Collectors;

public class ServerManager {

	public ServerInfo getLobbyServer() {
		Set<ServerInfo> lobbies = BungeeConnectorPlugin.getInstance().getProxy().getServers().values().stream().filter(server -> server.getName().startsWith("lobby")).collect(Collectors.toSet());
		if (lobbies.isEmpty())
			return null;

		for (ServerInfo lobby : lobbies)
			return lobby; //TODO: Load balancing

		return null;
	}

}