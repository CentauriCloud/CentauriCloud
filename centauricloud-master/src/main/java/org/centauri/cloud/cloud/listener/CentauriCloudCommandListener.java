package org.centauri.cloud.cloud.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.Listener;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.template.Template;

public class CentauriCloudCommandListener {
	
	@Listener
	public void onConsoleInput(ConsoleCommandEvent event) {
		final String input = event.getInput();
		switch(input.toLowerCase().split(" ")[0]){
			case "stop":
				Cloud.getInstance().stop();
				break;
			case "info":
				this.displayInfo();
				break;
			case "help":
				this.displayHelp();
				break;
			case "pl":
			case "plugins":
				this.displayPlugins();
				break;
			case "libs":
			case "librarys":
			case "libraries":
				this.displayLibs();
				break;
			case "servers":
				this.displayServers();
				break;
			case "templates":
				this.handleTemplatesCommand(input);
				break;
			default:
				Cloud.getLogger().error("Cannot find command: {}\n Type \"help\" for help", input);
				break;
		}
	}
	
	private void displayHelp() {
		Cloud.getLogger().info("Basic commands: \n"
				+ "help - this help screen\n"
				+ "plugins(pl) - display all plugins\n"
				+ "libraries/librarys(libs) - display all libs\n"
				+ "info - display informations about CentauriCloud and the team\n"
				+ "servers - display all connected servers\n"
				+ "stop - stop the master\n"
				+ "templates <create/remove/build/compress> - some command for templates");
	}
	
	private void displayPlugins() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Plugins: \n");
		Cloud.getInstance().getModuleManager().getLoaded().forEach(pl -> {
			sb.append(pl);
			sb.append(", ");
		});
		Cloud.getLogger().info(sb.toString());
	}
	
	private void displayInfo() {
		Cloud.getLogger().info("CentauriCloud v1.0 developed by Centauri Developer Team");
		Cloud.getLogger().info("Centauri Developer Team: ");
		Cloud.getLogger().info("Founder: Microsamp(Steve) & Fxshlein(Liam) & byImmortal(Joel)");
		Cloud.getLogger().info("Developer: MoVo99(Moritz) & Tobi14601(Tobi)");
		Cloud.getLogger().info("Contributors: -");
	}
	
	private void displayServers() {
		final Map<String, Set<Server>> serverTypeToServers = new HashMap<>();
	
		Cloud.getInstance().getServerManager().getChannelToServer().values().forEach(server -> {
			if(!serverTypeToServers.containsKey(server.getPrefix()))
				serverTypeToServers.put(server.getPrefix(), new HashSet<>());
			serverTypeToServers.get(server.getPrefix()).add(server);
		});
		
		final StringBuilder sb = new StringBuilder();
		sb.append("Servers: ");
		serverTypeToServers.forEach((type, servers) -> {
			sb.append('\n');
			sb.append(type);
			sb.append(": ");
			servers.forEach(server -> {
				sb.append(server.getName());
				sb.append(", ");
			});
		});
		
		Cloud.getLogger().info(sb.toString());
	}

	private void displayLibs() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Libs: \n");
		Cloud.getInstance().getLibraryLoader().getLoadedLibs().forEach(lib -> {
			String[] splittedName = lib.split("/");
			sb.append(splittedName[splittedName.length - 1]);
			sb.append(", ");
		});
		Cloud.getLogger().info(sb.toString());
	}
	
	private void handleTemplatesCommand(String input) {
		try {
			String[] args = input.split(" ");
			if(args.length == 3) {
				if(args[1].equalsIgnoreCase("create")) {
					Cloud.getInstance().getTemplateManager().loadTemplate(args[2]);
					Cloud.getLogger().info("Created template!");
				} else if(args[1].equalsIgnoreCase("remove")) {
					Cloud.getInstance().getTemplateManager().removeTemplate(args[2]);
					Cloud.getLogger().info("Removed template!");
				} else if(args[1].equalsIgnoreCase("build")) {
					Template template = Cloud.getInstance().getTemplateManager().getTemplate(args[2]);
					if(template == null) {
						Cloud.getLogger().warn("Cannot find module {}!", args[2]);
						return;
					}
					template.build();
					Cloud.getLogger().info("Built template!");
				} else if(args[1].equalsIgnoreCase("compress")) {
					Template template = Cloud.getInstance().getTemplateManager().getTemplate(args[2]);
					if(template == null) {
						Cloud.getLogger().warn("Cannot find module {}!", args[2]);
						return;
					}
					template.compress();
				}
			} else if(args.length == 2) {
				 if(args[1].equalsIgnoreCase("list")) {
					final StringBuilder sb = new StringBuilder();
					Cloud.getInstance().getTemplateManager().getTemplates().forEach(template -> { 
						sb.append(template.getName());
						sb.append(", ");
					});
					Cloud.getLogger().info("Templates: {}", sb.toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}