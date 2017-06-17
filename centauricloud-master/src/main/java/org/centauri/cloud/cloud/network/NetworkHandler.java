package org.centauri.cloud.cloud.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.DaemonLoadEvent;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketCloseConnection;
import org.centauri.cloud.common.network.packets.PacketServerLoad;
import org.centauri.cloud.common.network.packets.PacketServerRegister;
import org.centauri.cloud.common.network.server.ServerType;
import org.centauri.cloud.cloud.server.BungeeServer;
import org.centauri.cloud.cloud.server.Daemon;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.common.network.packets.PacketPlayerServerJoin;
import org.centauri.cloud.common.network.packets.PacketPlayerBungeeLeave;

@ChannelHandler.Sharable
public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		Channel channel = ctx.channel();
		Server server = Cloud.getInstance().getServerManager().getChannelToServer().get(channel);
		
		if (packet instanceof PacketPing && server != null) {
			PacketPing pingPacket = (PacketPing) packet;
			long ping = System.currentTimeMillis() - pingPacket.getTimestamp();
			server.setPing(ping);
		} else if (packet instanceof PacketServerRegister) {
			PacketServerRegister registerPacket = (PacketServerRegister) packet;
			if(registerPacket.getType() == ServerType.SPIGOT) {
				SpigotServer spigotServer = new SpigotServer(channel);
				spigotServer.setPrefix(registerPacket.getPrefix());
				spigotServer.setBukkitPort(registerPacket.getBukkitPort());
				Cloud.getInstance().getServerManager().registerServer(spigotServer);
			} else if(registerPacket.getType() == ServerType.BUNGEECORD) {
				BungeeServer bungeeServer = new BungeeServer(channel);
				bungeeServer.setPrefix("bungee");
				Cloud.getInstance().getServerManager().registerServer(bungeeServer);
			} else if(registerPacket.getType() == ServerType.DAEMON) {
				Daemon daemon = new Daemon(channel);
				daemon.setPrefix("daemon");
				Cloud.getInstance().getServerManager().registerServer(daemon);
			}
		} else if (packet instanceof PacketCloseConnection) {
			channel.close();
		} else if (packet instanceof PacketServerLoad) {
			PacketServerLoad serverLoad = (PacketServerLoad) packet;
			Cloud.getInstance().getEventManager().callEvent(new DaemonLoadEvent(serverLoad.getCpuLoad(), serverLoad.getFreeRam(), server));
		} else if (packet instanceof PacketPlayerServerJoin) {
			PacketPlayerServerJoin playerBungeeJoin = (PacketPlayerServerJoin) packet;
			Cloud.getInstance().getPlayerManager().addPlayer(playerBungeeJoin, server);
		} else if (packet instanceof PacketPlayerBungeeLeave) {
			PacketPlayerBungeeLeave playerBungeeLeave = (PacketPlayerBungeeLeave) packet;
			Cloud.getInstance().getPlayerManager().removePlayer(playerBungeeLeave.getUuid());
		}
	
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Cloud.getInstance().getServerManager().removeServer(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause instanceof IOException) {
			ctx.close();
			Cloud.getLogger().warn("Channel closed with message: {}", cause.getMessage());
		} else {
			Cloud.getLogger().catching(cause);
		}
	}

}
