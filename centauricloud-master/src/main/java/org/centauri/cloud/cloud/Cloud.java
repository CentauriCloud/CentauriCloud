package org.centauri.cloud.cloud;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.config.WhitelistConfig;
import org.centauri.cloud.cloud.download.ConnectorDownloader;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.io.Console;
import org.centauri.cloud.cloud.listener.CentauriCloudCommandListener;
import org.centauri.cloud.cloud.loadbalancing.ServerLoadBalancer;
import org.centauri.cloud.cloud.module.ModuleLoader;
import org.centauri.cloud.cloud.module.library.LibraryDownloader;
import org.centauri.cloud.cloud.module.library.LibraryLoader;
import org.centauri.cloud.cloud.network.NettyServer;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;
import org.centauri.cloud.cloud.server.ServerManager;
import org.centauri.cloud.cloud.template.TemplateManager;
import org.centauri.cloud.common.network.PacketManager;
import org.centauri.cloud.common.network.packets.Packet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.centauri.cloud.cloud.download.ModuleDownloader;

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
	@Getter private ModuleDownloader moduleDownloader;
	@Getter private final String VERSION = "1.0";

	//configurations
	@Getter @Setter private int port = 8012;
	@Getter @Setter private int timeout = 30;
	@Getter @Setter private int pingerIntervall = 25;
	@Getter @Setter private boolean whitelistActivated;
	@Getter @Setter private File sharedDir;
	@Getter @Setter private File tmpDir;
	@Getter @Setter private File libDir;
	@Getter @Setter private File templatesDir;

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

		//Call after loading propertyManager
		this.createDefaultDirectories();

		if (this.whitelistActivated) {
			this.whitelistedHosts = new HashSet<>();
			try {
				new WhitelistConfig().init();
			} catch (IOException ex) {
				getLogger().error(ex.getMessage(), ex);
				this.running = false;
			}
		}

		this.eventManager = new EventManager();

		this.libraryDownloader = new LibraryDownloader();

		this.libraryLoader = new LibraryLoader();
		this.libraryLoader.loadLibs(this.libDir);

		this.moduleManager = new ModuleLoader();
		this.moduleManager.initializeScheduler();

		this.serverManager = new ServerManager();

		this.server = new NettyServer();
		new Thread(() -> {
			try {
				server.run(this.port);
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				this.stop(); //Stop server
			}
		}, "Netty-Thread").start();

		this.registerListeners();

		createPacketsFile();

		this.templateManager = new TemplateManager();

		boolean importTemplates = Boolean.valueOf(PropertyManager.getInstance().getProperties().getProperty("autoloadTemplates", "true"));
		if (importTemplates)
			this.templateManager.importAllTemplates();

		ConnectorDownloader connectorDownloader = new ConnectorDownloader();
		connectorDownloader.checkConnectorsAndDownload();

		this.moduleDownloader = new ModuleDownloader();

		this.serverLoadBalancer = new ServerLoadBalancer();
		this.serverLoadBalancer.initializeScheduler();

		this.profiler.checkEnabled();
		this.profiler.stop(profile);

		Cloud.getLogger().info("Cloud started");

		new Console().start();
		Cloud.getLogger().info("Cloud stopped");
		System.exit(0);
	}

	public void stop() {
		server.stop();
		running = false;
	}

	private void registerListeners() {
		this.eventManager.registerEventHandler(new CentauriCloudCommandListener());
	}

	private void createPacketsFile() {
		File packetsFile = new File(this.sharedDir, "Packets.txt");
		if (packetsFile.exists()) {
			try {
				packetsFile.createNewFile();
			} catch (IOException e) {
				log.error("Cannot create", e);
			}
		}
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(packetsFile))) {
			for (Class<? extends Packet> packet : PacketManager.getInstance().getPackets())
				writer.println(packet.getSimpleName());
		} catch (FileNotFoundException e) {
			log.error("file not found", e);
		}

	}

	private void createDefaultDirectories() {
		try {

			//Whitelist
			File whitelistConfig = new File("whitelist.config");

			if (!whitelistConfig.exists()) {
				whitelistConfig.createNewFile();
				FileOutputStream outputStream = new FileOutputStream(whitelistConfig);
				try {
					outputStream.write("127.0.0.1".getBytes());
				} finally {
					outputStream.close();
				}
			}

			//Delete tmp dir on every start
			FileUtils.deleteDirectory(Cloud.getInstance().getTmpDir());

			//static dirs
			this.getLibDir().mkdir();
			this.getSharedDir().mkdir();
			this.getTemplatesDir().mkdir();
			this.getTmpDir().mkdir();

		} catch (Exception ex) {
			Cloud.getLogger().error("Cannot create default dirs!", ex);
		}

	}

	private void printFancyCopyright() {
		getLogger().info("#####################################################################");
		getLogger().info("#   ____           _                   _  ____ _                 _  #");
		getLogger().info("#  / ___|___ _ __ | |_ __ _ _   _ _ __(_)/ ___| | ___  _   _  __| | #");
		getLogger().info("# | |   / _ \\ '_ \\| __/ _` | | | | '__| | |   | |/ _ \\| | | |/ _` | #");
		getLogger().info("# | |__|  __/ | | | || (_| | |_| | |  | | |___| | (_) | |_| | (_| | #");
		getLogger().info("#  \\____\\___|_| |_|\\__\\__,_|\\__,_|_|  |_|\\____|_|\\___/ \\__,_|\\__,_| #");
		getLogger().info("#                                                                   #");
		getLogger().info("#                           -- Master --                            #");
		getLogger().info("#                         by Centauri Team                          #");
		getLogger().info("#####################################################################\n");
	}

	public static Logger getLogger() {
		return log;
	}

	public static void main(String... args) {
		new Cloud().start(args);
	}

}
