package org.centauri.cloud.cloud.network.packets;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class Packets {

	@Getter private static final List<Class<? extends OutputPacket>> OUT_PACKETS =
			Arrays.asList(
					PacketServerRegister.class
			);
	@Getter private static final List<Class<? extends InputPacket>> IN_PACKETS =
			Arrays.asList(
					PacketServerRegister.class
			);

}
