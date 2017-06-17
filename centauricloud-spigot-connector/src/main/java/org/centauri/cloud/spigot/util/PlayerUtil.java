
package org.centauri.cloud.spigot.util;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {

	public static void kickPlayer(UUID uuid, String message) {
		Player player = getPlayer(uuid);
		if (player != null && player.isOnline())
			player.kickPlayer(message);
	}

	public static void messagePlayer(UUID uuid, String message, boolean raw) {
		Player player = getPlayer(uuid);
		if (player != null && player.isOnline())
			if (raw)
				player.sendMessage(message);
			else
				player.sendRawMessage(message);
	}

	private static Player getPlayer(UUID uuid) {
		return Bukkit.getPlayer(uuid);
	}

}
