package org.centauri.cloud.bungee.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.md_5.bungee.api.ProxyServer;
import org.centauri.cloud.bungee.BungeeConnectorPlugin;
import org.centauri.cloud.bungee.util.PlayerUtil;
import org.centauri.cloud.bungee.util.ServerUtil;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketBungeeRegisterServer;
import org.centauri.cloud.common.network.packets.PacketKillServer;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketPlayerKick;
import org.centauri.cloud.common.network.packets.PacketPlayerMessage;
import org.centauri.cloud.common.network.packets.PacketPlayerSendHeaderFooter;
import org.centauri.cloud.common.network.packets.PacketServerRegister;
import org.centauri.cloud.common.network.packets.PacketToServerDispatchCommand;
import org.centauri.cloud.common.network.server.ServerType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if (packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		} else if (packet instanceof PacketBungeeRegisterServer) {
			PacketBungeeRegisterServer registerServer = (PacketBungeeRegisterServer) packet;
			ServerUtil.addServer(registerServer.getName(), new InetSocketAddress(registerServer.getHost(), registerServer.getBukkitPort()), "CentauriCloud hosted server", false);
			BungeeConnectorPlugin.getPluginLogger().info("Registered server: " + registerServer.getName() + " Port: " + registerServer.getBukkitPort());
		} else if (packet instanceof PacketKillServer) {
			BungeeConnectorPlugin.getInstance().getProxy().stop("CentauriCloud force stop");
		} else if (packet instanceof PacketPlayerKick) {
			PacketPlayerKick playerKick = (PacketPlayerKick) packet;
			PlayerUtil.kickPlayer(playerKick.getUuid(), playerKick.getMessage());
		} else if (packet instanceof PacketPlayerMessage) {
			PacketPlayerMessage playerMessage = (PacketPlayerMessage) packet;
			PlayerUtil.messagePlayer(playerMessage.getUuid(), playerMessage.getMessage(), playerMessage.isRaw());
		} else if (packet instanceof PacketPlayerSendHeaderFooter) {
			PacketPlayerSendHeaderFooter headerFooter = (PacketPlayerSendHeaderFooter) packet;
			PlayerUtil.headerFooter(headerFooter.getUuid(), headerFooter.getHeader(), headerFooter.getFooter());
		} else if (packet instanceof PacketToServerDispatchCommand) {
			ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), ((PacketToServerDispatchCommand) packet).getCommand());
		}

		BungeeConnectorPlugin.getInstance().getPacketHandlers().forEach(handler -> handler.channelRead(ctx, packet));
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new PacketServerRegister(BungeeConnectorPlugin.getInstance().getCloudConfiguration().getPrefix(), ServerType.BUNGEECORD, -1));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			ctx.close();
			BungeeConnectorPlugin.getPluginLogger().log(Level.WARNING, "Channel closed with message: {}", cause.getMessage());
		} else {
			BungeeConnectorPlugin.getPluginLogger().log(Level.WARNING, "", cause);
		}
	}

}
