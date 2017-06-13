package org.centauri.cloud.cloud.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.event.Event;

@RequiredArgsConstructor
public class ServerDenyEvent implements Event {

	@Getter private final String host;
	
}