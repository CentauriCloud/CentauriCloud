package org.centauri.cloud.cloud.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.centauri.cloud.cloud.event.Event;
import org.centauri.cloud.cloud.server.Server;

@AllArgsConstructor
public class DaemonLoadEvent implements Event {

	@Getter private double cpuLoad;
	@Getter private long freeRam;
	@Getter private Server server;

}