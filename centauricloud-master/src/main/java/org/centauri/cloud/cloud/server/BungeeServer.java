package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import lombok.Setter;
import org.centauri.cloud.common.network.packets.PacketBungeeRegisterServer;
import org.centauri.cloud.common.network.packets.PacketBungeeRemoveServer;

public class BungeeServer extends Server {
	
	@Setter private int players;
	
	public BungeeServer(Channel channel) {
		super(channel);
	}
	
	public void registerServer(SpigotServer server) {
		this.sendPacket(new PacketBungeeRegisterServer(server.getName(), ((InetSocketAddress) server.getChannel().remoteAddress()).getAddress().getHostAddress(), server.getBukkitPort()));
	}
	
	public void removeServer(SpigotServer server) {
		this.sendPacket(new PacketBungeeRemoveServer(server.getName()));
	}
	
	@Override
	public int getPlayers() {
		return this.players;
	}
}
