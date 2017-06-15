package org.centauri.cloud.common.network;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketBungeeRegisterServer;
import org.centauri.cloud.common.network.packets.PacketBungeeRemoveServer;
import org.centauri.cloud.common.network.packets.PacketCloseConnection;
import org.centauri.cloud.common.network.packets.PacketKillServer;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketServerDisconnect;
import org.centauri.cloud.common.network.packets.PacketServerRegister;
import org.centauri.cloud.common.network.packets.PacketToServerDispatchCommand;
import org.centauri.cloud.common.network.packets.PacketRequestConsole;
import org.centauri.cloud.common.network.packets.PacketSendConsole;
import org.centauri.cloud.common.network.packets.PacketServerLoad;
import org.centauri.cloud.common.network.packets.PacketStartServer;
import org.centauri.cloud.common.network.packets.PacketTemplateData;

public class PacketManager {

	@Getter private static final PacketManager instance = new PacketManager();
	@Getter private final List<Class<? extends Packet>> packets = new ArrayList<>(Arrays.asList(
			PacketServerRegister.class,
			PacketServerDisconnect.class,
			PacketToServerDispatchCommand.class,
			PacketRequestConsole.class,
			PacketSendConsole.class,
			PacketPing.class,
			PacketBungeeRegisterServer.class,
			PacketBungeeRemoveServer.class,
			PacketCloseConnection.class,
			PacketServerLoad.class,
			PacketTemplateData.class,
			PacketStartServer.class,
			PacketKillServer.class
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
