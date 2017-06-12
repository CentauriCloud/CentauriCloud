package org.centauri.cloud.cloud;

import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.io.Console;

public class Cloud {

	@Getter private static Cloud instance;
	@Getter @Setter private boolean running;
	@Getter private EventManager eventManager;
	
	public Cloud() {
		instance = this;
	}
	
	private void start(String... args) {
		PropertyManager manager = new PropertyManager();
		manager.load();
		
		ModuleLoader loader = new ModuleLoader();
		loader.initializeScheduler();
		
		this.eventManager = new EventManager();
		
		System.out.println("Cloud started");
		
		new Console();
		System.out.println("Cloud stopped");
	}
	
	public static void main(String... args) {
		new Cloud().start(args);
	}
	
}
