package org.centauri.cloud.cloud.listener;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.Listener;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;

public class TestListener {
	//Only for tests

	@Listener
	public void onConsoleInput(ConsoleCommandEvent event) {
		String input = event.getInput().toLowerCase();
		switch(input){
			case "stop":
				Cloud.getInstance().stop();
				break;
			default:
				System.out.println("Message: " + event.getInput());
				break;
		}
	}
	
}
