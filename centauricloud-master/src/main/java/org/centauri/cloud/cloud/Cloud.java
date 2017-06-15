package org.centauri.cloud.cloud;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.centauri.cloud.cloud.plugin.ModuleLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.centauri.cloud.cloud.config.WhitelistConfig;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.io.Console;
import org.centauri.cloud.cloud.listener.CentauriCloudCommandListener;
import org.centauri.cloud.cloud.loadbalancing.ServerLoadBalancer;
import org.centauri.cloud.cloud.network.NettyServer;
import org.centauri.cloud.cloud.plugin.library.LibraryDownloader;
import org.centauri.cloud.cloud.plugin.library.LibraryLoader;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;
import org.centauri.cloud.cloud.server.ServerManager;
import org.centauri.cloud.cloud.template.TemplateManager;

@Log4j2
public class Cloud {

	@Getter private static Cloud instance;
	@Getter @Setter private boolean running;
	@Getter private EventManager eventManager;
	@Getter private ServerManager serverManager;
	@Getter private NettyServer server;
	@Getter private ModuleLoader moduleManager;
	@Getter private LibraryLoader libraryLoader;
	@Getter private LibraryDownloader libraryDownloader;
	@Getter private TemplateManager templateManager;
	@Getter private ServerLoadBalancer serverLoadBalancer;
	@Getter private Set<String> whitelistedHosts;
	@Getter private CentauriProfiler profiler;
	
	//configurations
	@Getter @Setter private int timeout = 30;
	@Getter @Setter private int pingerIntervall = 25;
	@Getter @Setter private boolean whitelistActivated;
	
	public Cloud() {
		instance = this;
	}
	
	private void start(String... args) {
		this.profiler = CentauriProfiler.getInstance();
		CentauriProfiler.Profile profile = this.profiler.start("Master_start");
		
		this.printFancyCopyright();
		
		PropertyManager manager = new PropertyManager();
		manager.load();
		manager.initVariables(this);
		
		this.running = true;
		
		if(this.whitelistActivated) {
			this.whitelistedHosts = new HashSet<>();
			try {
				new WhitelistConfig();
			} catch (IOException ex) {
				getLogger().error(ex.getMessage(), ex);
				this.running = false;
			}
		}

		this.eventManager = new EventManager();
		
		this.libraryDownloader = new LibraryDownloader();
		//this.libraryDownloader.downloadLib("https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar", manager.getProperties().getProperty("libDir", "libs/"), "Gson-2.6.2.jar");
		
		this.libraryLoader = new LibraryLoader();
		this.libraryLoader.loadLibs(new File(manager.getProperties().getProperty("libDir", "libs/")));
		
		this.moduleManager = new ModuleLoader();
		this.moduleManager.initializeScheduler();
		
		this.serverManager = new ServerManager();
		
		this.server = new NettyServer();
		new Thread(() -> {
			try {
				server.run(Integer.valueOf(manager.getProperties().getProperty("port")));
			} catch (Exception ex) {
				getLogger().error(ex.getMessage(), ex);
				this.stop(); //Stop server
			}
		}, "Netty-Thread").start();
		
		this.registerListeners();
		
		this.templateManager = new TemplateManager();
		
		this.serverLoadBalancer = new ServerLoadBalancer();
		this.serverLoadBalancer.initializeScheduler();
		
		this.profiler.checkEnabled();
		this.profiler.stop(profile);
		
		Cloud.getLogger().info("Cloud started");
		
		new Console().start();
		Cloud.getLogger().info("Cloud stopped");
		System.exit(0);
	}

	public void stop(){
		server.stop();
		running = false;
	}
	
	private void registerListeners() {
		this.eventManager.registerEventHandler(new CentauriCloudCommandListener());
	}
	
	private void printFancyCopyright() {
		System.out.println(	"#####################################################################\n" +
					"#   ____           _                   _  ____ _                 _  #\n" +
					"#  / ___|___ _ __ | |_ __ _ _   _ _ __(_)/ ___| | ___  _   _  __| | #\n" +
					"# | |   / _ \\ '_ \\| __/ _` | | | | '__| | |   | |/ _ \\| | | |/ _` | #\n" +
					"# | |__|  __/ | | | || (_| | |_| | |  | | |___| | (_) | |_| | (_| | #\n" +
					"#  \\____\\___|_| |_|\\__\\__,_|\\__,_|_|  |_|\\____|_|\\___/ \\__,_|\\__,_| #\n" +
					"#                                                                   #\n" +
					"#                           -- Master --                            #\n" +
					"#                         by Centauri Team                          #\n" +
					"#####################################################################\n");
	}

	public static Logger getLogger() {
		return log;
	}
	
	public static void main(String... args) {
		new Cloud().start(args);
	}
	
}
