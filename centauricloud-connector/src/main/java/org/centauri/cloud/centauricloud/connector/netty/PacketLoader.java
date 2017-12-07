package org.centauri.cloud.centauricloud.connector.netty;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centauri.cloud.common.network.PacketManager;
import org.centauri.cloud.common.network.packets.Packet;

public class PacketLoader {

    public void readFile(Logger logger) {
        File file = new File("Packets.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath(), StandardOpenOption.READ)))) {
            List<Class<? extends Packet>> packets = new ArrayList<>(PacketManager.getInstance().getPackets());
            String line;
            while ((line = reader.readLine()) != null) {
                final String s = line;
                Class<? extends Packet> clazz = packets.stream().filter(packet -> {
                    return packet.getSimpleName().equals(s);
                }).findAny().orElse(null);
                PacketManager.getInstance().getPackets().add(clazz);
            }
            PacketManager.getInstance().setPackets(packets);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Cannot read Packets.txt", e);
        }
    }

}
