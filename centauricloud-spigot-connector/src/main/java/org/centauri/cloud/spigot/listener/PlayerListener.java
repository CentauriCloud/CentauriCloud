
package org.centauri.cloud.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.centauri.cloud.common.network.packets.PacketPlayerServerJoin;
import org.centauri.cloud.spigot.SpigotConnectorPlugin;

public class PlayerListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		new BukkitRunnable() {
			@Override
			public void run() {
				SpigotConnectorPlugin.getInstance().getClient().getChannel().writeAndFlush(new PacketPlayerServerJoin(event.getPlayer().getUniqueId(), "", "", false));
			}
		}.runTaskAsynchronously(SpigotConnectorPlugin.getInstance());
	}

}
