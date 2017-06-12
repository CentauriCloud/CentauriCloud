package org.centauri.cloud.cloud.listener;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.Listener;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;

public class OpenCloudCommandListener {
	
	@Listener
	public void onConsoleInput(ConsoleCommandEvent event) {
		String input = event.getInput().toLowerCase();
		switch(input.toLowerCase().split(" ")[0]){
			case "stop":
				Cloud.getInstance().stop();
				break;
			default:
				System.out.println("Cannot find command: " + input);
				break;
		}
	}
	
}
