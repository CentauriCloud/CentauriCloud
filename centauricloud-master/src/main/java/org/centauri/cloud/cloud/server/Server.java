package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import lombok.Data;
import org.centauri.cloud.cloud.network.packets.Packet;

@Data
public class Server {

	private Channel channel;
	private String prefix, name;
	private int id;
	private long ping;
	
	protected Server(Channel channel) {
		this.channel = channel;
	}
	
	public void kill() {
		
	}
	
	public void sendPacket(Packet packet) {
		channel.writeAndFlush(packet);
	}
	
}
