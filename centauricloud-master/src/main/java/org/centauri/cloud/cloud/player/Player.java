package org.centauri.cloud.cloud.player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.server.BungeeServer;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.common.network.packets.PacketPlayerKick;
import org.centauri.cloud.common.network.packets.PacketPlayerMessage;
import org.centauri.cloud.common.network.packets.PacketPlayerSendHeaderFooter;

public class Player {

	@Getter private final UUID UUID;
	@Getter private final BungeeServer bungeeServer;
	@Getter @Setter private SpigotServer spigotServer;
	@Getter private final String IP;
	@Getter private final String name;
	@Getter private final Map<String, Object> extraData = new ConcurrentHashMap<>();

	public Player(UUID uuid, BungeeServer bungeeServer, SpigotServer spigotServer, String ip, String name) {
		this.UUID = uuid;
		this.bungeeServer = bungeeServer;
		this.spigotServer = spigotServer;
		this.IP = ip;
		this.name = name;
	}

	public void kick() {
		this.kick("Disconnected.");
	}
	
	public void kick(String message) {
		if (bungeeServer != null)
			this.bungeeServer.getChannel().writeAndFlush(new PacketPlayerKick(this.UUID, message));
		else
			this.spigotServer.getChannel().writeAndFlush(new PacketPlayerKick(this.UUID, message));
	}

	public void sendMessage(String message) {
		if (bungeeServer != null)
			this.bungeeServer.getChannel().writeAndFlush(new PacketPlayerMessage(this.UUID, message, false));
		else
			this.spigotServer.getChannel().writeAndFlush(new PacketPlayerMessage(this.UUID, message, false));
	}

	public void sendRawMessage(String message) {
		if (bungeeServer != null)
			this.bungeeServer.getChannel().writeAndFlush(new PacketPlayerMessage(this.UUID, message, true));
		else
			this.spigotServer.getChannel().writeAndFlush(new PacketPlayerMessage(this.UUID, message, true));
	}

	public void setHeaderFooter(String header, String footer) {
		if (bungeeServer != null)
			this.bungeeServer.getChannel().writeAndFlush(new PacketPlayerSendHeaderFooter(this.UUID, header, footer));
	}

}
