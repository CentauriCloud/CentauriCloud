package org.centauri.cloud.cloud.network;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.centauri.cloud.cloud.network.packets.Packet;
import org.centauri.cloud.cloud.network.packets.PacketPing;
import org.centauri.cloud.cloud.network.packets.PacketServerDisconnect;
import org.centauri.cloud.cloud.network.packets.PacketServerRegister;
import org.centauri.cloud.cloud.network.packets.PacketToServerDispatchCommand;
import org.centauri.cloud.cloud.network.packets.PacketRequestConsole;
import org.centauri.cloud.cloud.network.packets.PacketSendConsole;

public class PacketManager {

	@Getter private static PacketManager instance = new PacketManager();
	@Getter private List<Class<? extends Packet>> packets = new ArrayList<>(Arrays.asList(
			PacketServerRegister.class,
			PacketServerDisconnect.class,
			PacketToServerDispatchCommand.class,
			PacketRequestConsole.class,
			PacketSendConsole.class,
			PacketPing.class
	));

	public int register(Class<? extends Packet> packetClass) {
		packets.add(packetClass);
		return getId(packetClass);
	}

	public byte getId(Class<? extends Packet> packetClass) {
		return (byte) packets.indexOf(packetClass);
	}
	
	public Class<? extends Packet> getPacketClass(byte id) {
		return this.packets.get(id);
	}

}
