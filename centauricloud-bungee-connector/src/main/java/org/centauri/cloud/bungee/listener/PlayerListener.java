package org.centauri.cloud.bungee.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.centauri.cloud.bungee.BungeeConnectorPlugin;
import org.centauri.cloud.common.network.packets.PacketPlayerServerJoin;
import org.centauri.cloud.common.network.packets.PacketPlayerBungeeLeave;

public class PlayerListener implements Listener {

	@EventHandler(priority = Byte.MIN_VALUE)
	public void onJoin(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();
		ServerInfo lobby = BungeeConnectorPlugin.getInstance().getServerManager().getLobbyServer();
		if (lobby == null) {
			player.disconnect("Cannot find lobby!");
		} else {
			player.connect(lobby);
		}
	}

	@EventHandler
	public void onJoin(LoginEvent event) {
		BungeeConnectorPlugin.getInstance().getClient().getChannel().writeAndFlush(new PacketPlayerServerJoin(
				event.getConnection().getUniqueId(),
				event.getConnection().getAddress().getHostString(),
				event.getConnection().getName(),
				true));
	}

	@EventHandler
	public void onLeave(PlayerDisconnectEvent event) {
		ProxyServer.getInstance().getScheduler().runAsync(BungeeConnectorPlugin.getInstance(), () -> {
			BungeeConnectorPlugin.getInstance().getClient().getChannel().writeAndFlush(new PacketPlayerBungeeLeave(event.getPlayer().getUniqueId()));
		});
	}

}
