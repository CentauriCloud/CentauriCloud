package org.centauri.cloud.cloud;

import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.io.Console;
import org.centauri.cloud.cloud.listener.TestListener;
import org.centauri.cloud.cloud.network.Server;

public class Cloud {

	@Getter private static Cloud instance;
	@Getter @Setter private boolean running;
	@Getter private EventManager eventManager;
	@Getter private Server server;
	
	public Cloud() {
		instance = this;
	}
	
	private void start(String... args) {
		PropertyManager manager = new PropertyManager();
		manager.load();
		
		this.eventManager = new EventManager();
		
		ModuleLoader loader = new ModuleLoader();
		loader.initializeScheduler();
		
		this.server = new Server();
		try {
			server.run(Integer.valueOf(manager.getProperties().getProperty("port")));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.running = false;//Stop server
		}
		
		this.registerListeners();
		
		System.out.println("Cloud started");
		
		new Console();
		System.out.println("Cloud stopped");
	}
	
	private void registerListeners() {
		this.eventManager.registerEventHandler(new TestListener());
	}
	
	public static void main(String... args) {
		new Cloud().start(args);
	}
	
}
