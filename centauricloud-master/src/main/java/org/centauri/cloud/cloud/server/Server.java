package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import lombok.Data;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketKillServer;

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
		this.sendPacket(new PacketKillServer());
	}
	
	public String getHost() {
		return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
	}
	
	public void sendPacket(Packet packet) {
		channel.writeAndFlush(packet);
	}
	
}
