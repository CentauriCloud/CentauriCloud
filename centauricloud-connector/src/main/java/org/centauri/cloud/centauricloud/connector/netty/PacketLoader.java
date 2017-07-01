package org.centauri.cloud.centauricloud.connector.netty;

import org.centauri.cloud.common.network.PacketManager;
import org.centauri.cloud.common.network.packets.Packet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketLoader {

	public void readFile(Logger logger) {
		File file = new File("Packets.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			List<Class<? extends Packet>> packets = new ArrayList<>(PacketManager.getInstance().getPackets());
			PacketManager.getInstance().getPackets().clear();
			reader.lines().forEach(s -> {
				Class<? extends Packet> clazz = packets.stream().filter(packet -> packet.getSimpleName().equals(s)).findAny().orElse(null);
				PacketManager.getInstance().getPackets().add(clazz);
			});
		} catch (IOException e) {
			logger.log(Level.WARNING, "Cannot read packets.txt", e);
		}

	}

}
