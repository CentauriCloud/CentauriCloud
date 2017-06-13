package org.centauri.cloud.spigot.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bukkit.Bukkit;
import org.centauri.cloud.cloud.network.packets.Packet;
import org.centauri.cloud.cloud.network.packets.PacketPing;
import org.centauri.cloud.cloud.network.packets.PacketServerRegister;
import org.centauri.cloud.cloud.network.server.ServerType;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if(packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new PacketServerRegister(SpigotConnectorPlugin.getInstance().getCloudConfiguration().getPrefix(), ServerType.SPIGOT, Bukkit.getPort()));
	}

}
