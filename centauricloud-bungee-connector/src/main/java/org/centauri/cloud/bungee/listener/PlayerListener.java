package org.centauri.cloud.bungee.listener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.centauri.cloud.bungee.BungeeConnectorPlugin;

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

}