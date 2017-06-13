package org.centauri.cloud.cloud.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.network.packets.Packet;
import org.centauri.cloud.cloud.network.packets.PacketPing;
import org.centauri.cloud.cloud.network.packets.PacketServerRegister;
import org.centauri.cloud.cloud.network.server.ServerType;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.server.SpigotServer;

@ChannelHandler.Sharable
public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		Channel channel = ctx.channel();
		Server server = Cloud.getInstance().getServerManager().getChannelToServer().get(channel);
		
		if (packet instanceof PacketPing) {
			PacketPing pingPacket = (PacketPing) packet;
			long ping = System.currentTimeMillis() - pingPacket.getTimestamp();
			server.setPing(ping);
		} else if (packet instanceof PacketServerRegister) {
			PacketServerRegister registerPacket = (PacketServerRegister) packet;
			if(registerPacket.getType() == ServerType.SPIGOT) {
				SpigotServer spigotServer = new SpigotServer(channel);
				spigotServer.setPrefix(spigotServer.getPrefix());
				Cloud.getInstance().getServerManager().registerServer(spigotServer);
				Cloud.getLogger().debug("Debug: SpigotServer registered");
			}
		}
	
	}
	
}
