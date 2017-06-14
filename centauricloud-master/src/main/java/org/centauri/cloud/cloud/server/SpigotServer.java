package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.Cloud;

public class SpigotServer extends Server {
	
	@Getter @Setter private int bukkitPort;
	@Getter @Setter private int players;

	public SpigotServer(Channel channel) {
		super(channel);
	}
	
	public void setName(String name) {
		super.setName(name);
		Cloud.getInstance().getServerManager().getChannelToServer().values().forEach((server) -> {
			if(server instanceof BungeeServer) {
				BungeeServer bungee = (BungeeServer) server;
				bungee.registerServer(SpigotServer.this);
			}
		});
	}
	
}
