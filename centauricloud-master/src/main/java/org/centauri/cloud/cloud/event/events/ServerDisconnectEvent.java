package org.centauri.cloud.cloud.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.event.Event;
import org.centauri.cloud.cloud.server.Server;

@RequiredArgsConstructor
public class ServerDisconnectEvent implements Event {

	@Getter	private final Server server;
	
}
