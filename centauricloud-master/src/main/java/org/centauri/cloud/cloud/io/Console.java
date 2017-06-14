package org.centauri.cloud.cloud.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;

public class Console {
	
	public Console() {
	}

	public void start() {
		this.read();
	}
	
	private void read() {
		BufferedReader reader = new BufferedReader (new InputStreamReader(System.in, Charset.forName("UTF-8")));
		
		try{
			while(Cloud.getInstance().isRunning()) {
				final String input = reader.readLine();
				Cloud.getInstance().getEventManager().callEvent(new ConsoleCommandEvent(input));
			}
		} catch (IOException ex) {
			Cloud.getLogger().error(ex.getMessage());
			Cloud.getInstance().stop(); //Exit caused by input-problem
		}
	}
	
}
