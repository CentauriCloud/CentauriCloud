package org.centauri.cloud.cloud.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.centauri.cloud.cloud.event.Event;

@RequiredArgsConstructor
public class ConsoleCommandEvent implements Event {
	
	@Getter private final String input;
	@Getter @Setter private boolean handled;
	
}
