package org.centauri.cloud.bungee.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.net.InetSocketAddress;
import org.centauri.cloud.bungee.util.ServerUtil;
import org.centauri.cloud.cloud.network.packets.Packet;
import org.centauri.cloud.cloud.network.packets.PacketBungeeRegisterServer;
import org.centauri.cloud.cloud.network.packets.PacketPing;


public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if(packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		} else if(packet instanceof PacketBungeeRegisterServer) {
			PacketBungeeRegisterServer registerServer = (PacketBungeeRegisterServer) packet;
			ServerUtil.addServer(registerServer.getName(), new InetSocketAddress(registerServer.getHost(), registerServer.getBukkitPort()), "CentauriCloud hosted server", true);
		}
	}

}
