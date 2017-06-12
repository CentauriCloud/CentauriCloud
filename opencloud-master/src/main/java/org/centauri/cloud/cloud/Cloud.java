package org.centauri.cloud.cloud;

import org.centauri.cloud.cloud.plugin.ModuleLoader;
import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.io.Console;
import org.centauri.cloud.cloud.listener.OpenCloudCommandListener;
import org.centauri.cloud.cloud.listener.TestListener;
import org.centauri.cloud.cloud.network.NettyServer;
import org.centauri.cloud.cloud.server.ServerManager;

public class Cloud {

	@Getter private static Cloud instance;
	@Getter @Setter private boolean running;
	@Getter private EventManager eventManager;
	@Getter private ServerManager serverManager;
	@Getter private NettyServer server;
	
	public Cloud() {
		instance = this;
	}
	
	private void start(String... args) {
		this.printFancyCopyright();
		PropertyManager manager = new PropertyManager();
		manager.load();
		
		this.running = true;
		
		this.eventManager = new EventManager();
		
		ModuleLoader loader = new ModuleLoader();
		loader.initializeScheduler();
		
		this.serverManager = new ServerManager();
		
		this.server = new NettyServer();
		new Thread(() -> {
			try {
				server.run(Integer.valueOf(manager.getProperties().getProperty("port")));
			} catch (Exception ex) {
				ex.printStackTrace();
				this.running = false;//Stop server
			}
		}, "Netty-Thread").start();
		
		this.registerListeners();
		
		System.out.println("Cloud started");
		
		new Console();
		System.out.println("Cloud stopped");
		System.exit(0);
	}
	
	public void stop(){
		server.stop();
		running = false;
	}
	
	private void registerListeners() {
		this.eventManager.registerEventHandler(new OpenCloudCommandListener());
		this.eventManager.registerEventHandler(new TestListener());
	}
	
	private void printFancyCopyright() {
		System.out.println("##############################");
		System.out.println("# OpenCloud by Centauri-Team #");
		System.out.println("##############################");
	}
	
	public static void main(String... args) {
		new Cloud().start(args);
	}
	
}
