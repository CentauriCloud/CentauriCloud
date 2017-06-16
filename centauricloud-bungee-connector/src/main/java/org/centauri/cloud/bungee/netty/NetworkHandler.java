package org.centauri.cloud.bungee.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.net.InetSocketAddress;
import org.centauri.cloud.bungee.BungeeConnectorPlugin;
import org.centauri.cloud.bungee.util.ServerUtil;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketBungeeRegisterServer;
import org.centauri.cloud.common.network.packets.PacketKillServer;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketServerRegister;
import org.centauri.cloud.common.network.server.ServerType;


public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if(packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		} else if(packet instanceof PacketBungeeRegisterServer) {
			PacketBungeeRegisterServer registerServer = (PacketBungeeRegisterServer) packet;
			ServerUtil.addServer(registerServer.getName(), new InetSocketAddress(registerServer.getHost(), registerServer.getBukkitPort()), "CentauriCloud hosted server", true);
			BungeeConnectorPlugin.getPluginLogger().info("Registered server: " + registerServer.getName()+" Port: " + registerServer.getBukkitPort());
		} else if(packet instanceof PacketKillServer) {
			BungeeConnectorPlugin.getInstance().getProxy().stop("CentauriCloud force stop");
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new PacketServerRegister(BungeeConnectorPlugin.getInstance().getCloudConfiguration().getPrefix(), ServerType.BUNGEECORD, -1));
	}

}
