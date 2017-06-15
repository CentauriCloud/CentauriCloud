package org.centauri.cloud.daemon.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.centauri.cloud.cloud.network.packets.Packet;
import org.centauri.cloud.cloud.network.packets.PacketPing;
import org.centauri.cloud.cloud.network.packets.PacketServerRegister;
import org.centauri.cloud.cloud.network.packets.PacketTemplateData;
import org.centauri.cloud.cloud.network.server.ServerType;
import org.centauri.cloud.daemon.Daemon;


public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if(packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		} else if(packet instanceof PacketTemplateData) {
			PacketTemplateData templateData = (PacketTemplateData) packet;
			System.out.println("Debug: Received template data; Size: " + templateData.getTemplateData().length);
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new PacketServerRegister(Daemon.getInstance().getCloudConfiguration().getPrefix(), ServerType.DAEMON, -1));
	}

}
