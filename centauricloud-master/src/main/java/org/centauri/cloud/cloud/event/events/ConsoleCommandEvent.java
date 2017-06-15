package org.centauri.cloud.cloud.event.events;

import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.event.Event;

public class ConsoleCommandEvent implements Event {

	@Getter private final String command;
	@Getter private final String[] args;
	@Getter @Setter private boolean handled;


	public ConsoleCommandEvent(String input) {
		command = input.split(" ")[0];
		this.args = input.substring(command.length()).trim().split(" ");
	}
}
