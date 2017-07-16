package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import lombok.Setter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.common.network.packets.PacketBungeeRegisterServer;
import org.centauri.cloud.common.network.packets.PacketBungeeRemoveServer;

import java.net.InetSocketAddress;

public class BungeeServer extends Server {

	@Setter private int players;

	public BungeeServer(Channel channel) {
		super(channel);
	}

	public void registerServer(SpigotServer server) {
		this.sendPacket(new PacketBungeeRegisterServer(server.getName(), ((InetSocketAddress) server.getChannel().remoteAddress()).getAddress().getHostAddress(), server.getBukkitPort()));
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		Cloud.getInstance().getServerManager().stream(stream -> {
			stream.filter(server -> server instanceof SpigotServer).forEach(spigotServer -> BungeeServer.this.registerServer((SpigotServer) spigotServer));
		});
	}

	public void removeServer(SpigotServer server) {
		this.sendPacket(new PacketBungeeRemoveServer(server.getName()));
	}

	@Override
	public int getPlayers() {
		return this.players;
	}
}
