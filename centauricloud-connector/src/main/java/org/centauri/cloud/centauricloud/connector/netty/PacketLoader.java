package org.centauri.cloud.centauricloud.connector.netty;

import org.centauri.cloud.common.network.PacketManager;
import org.centauri.cloud.common.network.packets.Packet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketLoader {

	public void readFile(Logger logger) {
		File file = new File("Packets.txt");
		//logger.info("[DEBUG] Reading packet ids...");
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			List<Class<? extends Packet>> packets = new ArrayList<>(PacketManager.getInstance().getPackets());
			AtomicBoolean failed = new AtomicBoolean(false);
			reader.lines().forEach(s -> {
				Class<? extends Packet> clazz = packets.stream().filter(packet -> packet != null && packet.getSimpleName().equals(s)).findAny().orElse(null);
				PacketManager.getInstance().getPackets().add(clazz);
				//logger.info("[DEBUG] Register packet | name: "+s+" clazz: "+clazz);
				//TODO: Remove debug if not needed
				if(clazz == null)
					failed.set(true);
			});
			//logger.info("[DEBUG] Successfully read the packets.txt!");
			if(!failed.get())
				PacketManager.getInstance().getPackets().clear();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Cannot read packets.txt", e);
		}

	}

}
