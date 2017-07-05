package org.centauri.cloud.spigot.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bukkit.Bukkit;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketKillServer;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketServerRegister;
import org.centauri.cloud.common.network.packets.PacketToServerDispatchCommand;
import org.centauri.cloud.common.network.server.ServerType;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if (packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		} else if (packet instanceof PacketKillServer) {
			Bukkit.shutdown();
		} else if (packet instanceof PacketToServerDispatchCommand) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ((PacketToServerDispatchCommand) packet).getCommand());
		}

		SpigotConnectorPlugin.getInstance().getPacketHandlers().forEach(handler -> handler.channelRead(ctx, packet));
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new PacketServerRegister(SpigotConnectorPlugin.getInstance().getCloudConfiguration().getPrefix(), ServerType.SPIGOT, Bukkit.getPort()));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			ctx.close();
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "Channel closed with message: {}", cause.getMessage());
		} else {
			SpigotConnectorPlugin.getPluginLogger().log(Level.WARNING, "", cause);
		}
	}


}
