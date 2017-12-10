package org.centauri.cloud.cloud.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.DaemonLoadEvent;
import org.centauri.cloud.cloud.event.events.PacketReceivingEvent;
import org.centauri.cloud.cloud.server.BungeeServer;
import org.centauri.cloud.cloud.server.Daemon;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketCloseConnection;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketServerLoad;
import org.centauri.cloud.common.network.packets.PacketServerRegister;

import java.io.IOException;

@ChannelHandler.Sharable
public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		Channel channel = ctx.channel();
		Server server = Cloud.getInstance().getServerManager().get(channel);

		if (packet instanceof PacketPing && server != null) {
			PacketPing pingPacket = (PacketPing) packet;
			long ping = System.currentTimeMillis() - pingPacket.getTimestamp();
			server.setPing(ping);
		} else if (packet instanceof PacketServerRegister) {
			PacketServerRegister registerPacket = (PacketServerRegister) packet;
			switch (registerPacket.getType()) {
				case BUNGEECORD:
					BungeeServer bungeeServer = new BungeeServer(channel);
					bungeeServer.setPrefix("bungee");
					Cloud.getInstance().getServerManager().add(bungeeServer);
					break;
				case SPIGOT:
					SpigotServer spigotServer = new SpigotServer(channel);
					spigotServer.setPrefix(registerPacket.getPrefix());
					spigotServer.setBukkitPort(registerPacket.getBukkitPort());
					Cloud.getInstance().getServerManager().add(spigotServer);
					break;
				case DAEMON:
					Daemon daemon = new Daemon(channel);
					daemon.setPrefix("daemon");
					Cloud.getInstance().getServerManager().add(daemon);
					break;
				default:
			}
		} else if (packet instanceof PacketCloseConnection) {
			channel.close();
		} else if (packet instanceof PacketServerLoad) {
			PacketServerLoad serverLoad = (PacketServerLoad) packet;
			Cloud.getInstance().getEventManager().callEvent(new DaemonLoadEvent(serverLoad.getCpuLoad(), serverLoad.getFreeRam(), server));
		}

		Cloud.getInstance().getEventManager().callEvent(new PacketReceivingEvent(packet, server));

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Cloud.getInstance().getServerManager().remove(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof DecoderException) {
			Cloud.getLogger().warn("Something went wrong on decoding packets, so the cloud has to stop to prevent data loss.");
			Cloud.getLogger().catching(cause);
			Cloud.getInstance().stop();
		} else if (cause instanceof IOException) {
			ctx.close();
			Cloud.getLogger().warn("Channel closed with message: {}", cause.getMessage());
		} else {
			Cloud.getLogger().catching(cause);
		}
	}

}
