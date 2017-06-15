package org.centauri.cloud.cloud.io;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;

import java.util.Scanner;

public class Console {

	public Console() {
	}

	public void start() {
		this.read();
	}

	private void read() {
		Scanner reader = new Scanner(System.in, "UTF-8");

		while (Cloud.getInstance().isRunning()) {
			final String input = reader.nextLine();
			ConsoleCommandEvent commandEvent = new ConsoleCommandEvent(input);
			Cloud.getInstance().getEventManager().callEvent(commandEvent);
			if (!commandEvent.isHandled())
				Cloud.getLogger().info("Cannot find command: {}\n Type \"help\" for help", input);
		}
	}

}
