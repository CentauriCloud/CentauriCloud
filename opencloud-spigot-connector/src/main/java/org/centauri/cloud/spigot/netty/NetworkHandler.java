package org.centauri.cloud.spigot.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.centauri.cloud.cloud.network.packets.Packet;
import org.centauri.cloud.cloud.network.packets.PacketPing;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if(packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		}
	}

}
