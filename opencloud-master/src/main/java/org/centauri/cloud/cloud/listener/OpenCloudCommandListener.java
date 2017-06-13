package org.centauri.cloud.cloud.listener;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.Listener;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;

public class OpenCloudCommandListener {
	
	@Listener
	public void onConsoleInput(ConsoleCommandEvent event) {
		final String input = event.getInput();
		switch(input.toLowerCase().split(" ")[0]){
			case "stop":
				Cloud.getInstance().stop();
				break;
			case "info":
				System.out.println("OpenCloud v1.0 developed by Centauri Developer Team");
				System.out.println("Centauri Developer Team: ");
				System.out.println("Founder: Microsamp(Steve) & Fxshlein(Liam) & byImmortal(Joel)");
				System.out.println("Developer: MoVo99(Moritz) & Tobi14601(Tobi)");
				System.out.println("Contributors: -");
				break;
			case "pl":
			case "plugins":
				final StringBuilder sb = new StringBuilder();
				sb.append("Plugins: \n");
				Cloud.getInstance().getModuleManager().getLoaded().forEach(pl -> {
					sb.append(pl);
					sb.append(", ");
				});
				System.out.println(sb.toString());
				break;
			default:
				System.out.println("Cannot find command: " + input);
				break;
		}
	}
	
}
