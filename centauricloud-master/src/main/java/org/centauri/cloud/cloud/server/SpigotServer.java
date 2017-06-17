package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.Cloud;

public class SpigotServer extends Server {
	
	@Getter @Setter private int bukkitPort;
	@Setter private int players;

	public SpigotServer(Channel channel) {
		super(channel);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		Cloud.getInstance().getServerManager().getChannelToServer().values().forEach((server) -> {
			if(server instanceof BungeeServer) {
				BungeeServer bungee = (BungeeServer) server;
				bungee.registerServer(this);
			}
		});
	}

	@Override
	public int getPlayers() {
		return this.players;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
}
