
package org.centauri.cloud.bungee.util;

import java.util.UUID;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerUtil {

	public static void kickPlayer(UUID uuid, String message) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
		if (player != null)
			player.disconnect(new TextComponent(message));
	}

	public static void messagePlayer(UUID uuid, String message, boolean raw) {
		ProxiedPlayer player = getPlayer(uuid);
		if (player != null)
			if (raw)
				player.sendMessage(TextComponent.fromLegacyText(message));
			else
				player.sendMessage(new TextComponent(message));
	}

	public static void headerFooter(UUID uuid, String header, String footer) {
		ProxiedPlayer player = getPlayer(uuid);
		if (player != null) {
			player.setTabHeader(new TextComponent(header), new TextComponent(footer));
		}
	}

	private static ProxiedPlayer getPlayer(UUID uuid) {
		return ProxyServer.getInstance().getPlayer(uuid);
	}

}
