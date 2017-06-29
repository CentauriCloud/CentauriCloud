package org.centauri.cloud.cloud.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.event.Event;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.common.network.packets.Packet;

@RequiredArgsConstructor
public class PacketReceivingEvent implements Event {

	@Getter private final Packet packet;
	@Getter private final Server server;

}