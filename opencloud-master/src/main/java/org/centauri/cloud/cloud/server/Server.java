package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import lombok.Data;

@Data
public class Server {

	private Channel channel;
	private String name;
	
	private Server(Channel channel) {
		this.channel = channel;
	}
	
}
