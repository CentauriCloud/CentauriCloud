package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

public class SpigotServer extends Server {
	
	@Getter @Setter private int bukkitPort;
	
	public SpigotServer(Channel channel) {
		super(channel);
	}
	
}
