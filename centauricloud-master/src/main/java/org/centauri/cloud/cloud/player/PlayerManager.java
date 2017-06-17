package org.centauri.cloud.cloud.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.PlayerDisconnectEvent;
import org.centauri.cloud.cloud.event.events.PlayerLoginEvent;
import org.centauri.cloud.cloud.event.events.PlayerSpigotLoginEvent;
import org.centauri.cloud.cloud.server.BungeeServer;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.common.network.packets.PacketPlayerServerJoin;

@NoArgsConstructor
public class PlayerManager {

	private Map<UUID, Player> players = new HashMap<>();
	private boolean onlyProxy = true;

	public void addPlayer(PacketPlayerServerJoin packet, Server server) {
		Player player = getPlayer(packet.getUuid());

		if (player != null && !packet.isBungeeJoin()) {
			player.setSpigotServer((SpigotServer) server);
			Cloud.getInstance().getEventManager().callEvent(new PlayerSpigotLoginEvent(player));
			return;
		}

		if (player == null) {
			if (packet.isBungeeJoin()) {
				player = new Player(packet.getUuid(), (BungeeServer) server, null, packet.getIp(), packet.getName());
			} else {
				player = new Player(packet.getUuid(), null, (SpigotServer) server, packet.getIp(), packet.getName());
				Cloud.getInstance().getEventManager().callEvent(new PlayerSpigotLoginEvent(player));
			}
			players.put(packet.getUuid(), player);
			Cloud.getInstance().getEventManager().callEvent(new PlayerLoginEvent(player));
		}

		if (onlyProxy) {
			if (player.getBungeeServer() == null) {
				player.kick("Bitte verbinde dich über den Proxy!");
			}
		}
	}

	public void removePlayer(UUID uuid) {
		Player player = getPlayer(uuid);
		if (player == null)
			return;
		Cloud.getInstance().getEventManager().callEvent(new PlayerDisconnectEvent(player));
		this.players.remove(uuid);
	}

	public Player getPlayer(UUID uuid) {
		return this.players.get(uuid);
	}

}
