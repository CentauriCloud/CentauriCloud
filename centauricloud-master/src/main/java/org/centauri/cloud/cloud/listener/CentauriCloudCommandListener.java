package org.centauri.cloud.cloud.listener;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.download.ModuleDownloader;
import org.centauri.cloud.cloud.event.Listener;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;
import org.centauri.cloud.cloud.profiling.ProfilerStatistic;
import org.centauri.cloud.cloud.server.Daemon;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.template.Template;
import org.centauri.cloud.common.network.packets.PacketToServerDispatchCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class CentauriCloudCommandListener {

	private String spaces;

	@Listener
	public void onConsoleInput(ConsoleCommandEvent event) {
		final String command = event.getCommand();
		boolean handled = true;
		switch (command.toLowerCase()) {
			case "stop":
				Cloud.getInstance().stop();
				break;
			case "version":
			case "ver":
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
			case "template":
			case "templates":
				this.handleTemplatesCommand(event.getArgs());
				break;
			case "profile":
				this.handleProfileCommand();
				break;
			case "server":
				this.handleServerCommand(event.getArgs());
				break;
			case "cmd":
			case "command":
				this.handleExecuteCommand(event.getArgs());
				break;
			case "install":
				this.handleModuleInstalling(event.getArgs());
				break;
			default:
				handled = false;
				break;
		}
		if (handled)
			event.setHandled(true);
	}


	private void displayHelp() {
		Cloud.getLogger().info("o----------------------------------------------------------------------------------------o");
		Cloud.getLogger().info("|                               Basic Commands:                                          |");
		Cloud.getLogger().info("|                                                                                        |");
		Cloud.getLogger().info("| help - displays this help screen                                                       |");
		Cloud.getLogger().info("| plugins (pl) - displays all plugins                                                    |");
		Cloud.getLogger().info("| libraries/librarys (libs) - displays all libs                                          |");
		Cloud.getLogger().info("| info - displays information about CentauriCloud and the team                           |");
		Cloud.getLogger().info("| version/ver - displays information about CentauriCloud version and the team            |");
		Cloud.getLogger().info("| servers - displays all connected servers                                               |");
		Cloud.getLogger().info("| server <start/kill> <template/serverId> - some commands for servers                    |");
		Cloud.getLogger().info("| stop - stops the cloud                                                                 |");
		Cloud.getLogger().info("| profile - displays information about current profile                                   |");
		Cloud.getLogger().info("| template <create/remove/build/compress/list> [--update] - some commands for templates  |");
		Cloud.getLogger().info("| cmd <server> <command> - executes a command on a server                                |");
		Cloud.getLogger().info("| install <module> - downloads a module                                                  |");
		Cloud.getLogger().info("o----------------------------------------------------------------------------------------o");
	}

	private void displayPlugins() {
		Cloud.getLogger().info("o----------------------------------------------------o");
		Cloud.getLogger().info("|                      Plugins:                      |");
		if (!Cloud.getInstance().getModuleManager().getLoaded().isEmpty()) {
			Cloud.getLogger().info("|                                                    |");
		}
		Cloud.getInstance().getModuleManager().getLoaded().forEach(pl -> {
			final StringBuilder sb = new StringBuilder();
			spaces = calculateSpaces(51, pl.getName());
			sb.append("| ").append(pl.getName()).append(spaces).append("|");
			Cloud.getLogger().info(sb.toString());
		});
		Cloud.getLogger().info("o----------------------------------------------------o");
	}

	private void displayInfo() {
		Cloud.getLogger().info("o---------------------------------------------------------------o");
		Cloud.getLogger().info("|    CentauriCloud v" + Cloud.getInstance().getVERSION() + " developed by Centauri Developer Team    |");
		Cloud.getLogger().info("|                                                               |");
		Cloud.getLogger().info("| Founder: Microsamp(Steve) & byImmortal(Joel) & Fxshlein(Liam) |");
		Cloud.getLogger().info("| Developer: MoVo99(Moritz) & Tobi14601(Tobi)                   |");
		Cloud.getLogger().info("| Contributors: LordOtut(Pascal)                                |");
		Cloud.getLogger().info("o---------------------------------------------------------------o");
	}

	private void displayServers() {
		final Map<String, Set<Server>> serverTypeToServers = new HashMap<>();

		Cloud.getInstance().getServerManager().getChannelToServer().values().forEach(server -> {
			if (!serverTypeToServers.containsKey(server.getPrefix()))
				serverTypeToServers.put(server.getPrefix(), new HashSet<>());
			serverTypeToServers.get(server.getPrefix()).add(server);
		});

		Cloud.getLogger().info("o----------------------------------------------------o");
		Cloud.getLogger().info("|                      Servers:                      |");
		if (!serverTypeToServers.isEmpty()) {
			Cloud.getLogger().info("|                                                    |");
		}
		serverTypeToServers.forEach((type, servers) -> {
			spaces = calculateSpaces(51 - 1, type);
			Cloud.getLogger().info("| " + type + ":" + spaces + "|");
			servers.forEach(server -> {
				spaces = calculateSpaces(51 - 2, server.getName());
				Cloud.getLogger().info("|   " + server.getName() + spaces + "|");
			});
		});
		Cloud.getLogger().info("o----------------------------------------------------o");
	}

	private void displayLibs() {
		Cloud.getLogger().info("o----------------------------------------------------o");
		Cloud.getLogger().info("|                       Libs:                        |");
		if (!Cloud.getInstance().getLibraryLoader().getLoadedLibs().isEmpty()) {
			Cloud.getLogger().info("|                                                    |");
		}
		Cloud.getInstance().getLibraryLoader().getLoadedLibs().forEach(lib -> {
			String[] splittedName = lib.split("/");
			String libname = splittedName[splittedName.length - 1];
			spaces = calculateSpaces(51, libname);
			Cloud.getLogger().info("| " + libname + spaces + "|");
		});
		Cloud.getLogger().info("o----------------------------------------------------o");
	}

	private void handleTemplatesCommand(String[] args) {
		TemplateSubcommands subCmd = Stream.of(TemplateSubcommands.values())
				.filter(templateSubcommands -> templateSubcommands.command.equals(args[0]))
				.findAny()
				.orElse(null);
		if (subCmd == null) {
			sendTemplateHelp(null);
			return;
		}
		try {
			switch (subCmd) {
				case CREATE:
					if (args.length != 2) {
						sendTemplateHelp(subCmd.command);
						return;
					}
					Template template = Cloud.getInstance().getTemplateManager().getTemplate(args[1]);
					if (template != null) {
						Cloud.getLogger().warn("Template {} already exists!", args[1]);
						return;
					}
					Cloud.getInstance().getTemplateManager().loadTemplate(args[1]);
					break;
				case REMOVE:
					if (args.length != 2) {
						sendTemplateHelp(subCmd.command);
						return;
					}
					Cloud.getInstance().getTemplateManager().removeTemplate(args[1]);
					break;
				case BUILD:
					if (args.length != 2
							&& args.length != 3) {
						sendTemplateHelp(subCmd.command);
						return;
					}
					template = Cloud.getInstance().getTemplateManager().getTemplate(args[1]);
					if (template == null) {
						Cloud.getLogger().warn("Cannot find template {}!", args[1]);
						return;
					}
					template.build();
					Cloud.getLogger().info("Built template {}!", args[1]);

					if (args.length >= 3) {
						if (args[2].equalsIgnoreCase("--update")) {
							template.compress();
							Cloud.getInstance().getServerManager().getChannelToServer().values()
									.stream().filter(server -> server instanceof Daemon).forEach(daemon -> {
								((Daemon) daemon).sendTemplate(template);
								Cloud.getLogger().info("Update template {} on daemon {}!", template.getName(), daemon.getName());
							});
							Cloud.getLogger().info("Updated the template on all daemons!");
						}
					}

					break;
				case COMPRESS:
					if (args.length != 2) {
						sendTemplateHelp(subCmd.command);
						return;
					}
					template = Cloud.getInstance().getTemplateManager().getTemplate(args[1]);
					if (template == null) {
						Cloud.getLogger().warn("Cannot find template {}!", args[1]);
						return;
					}
					template.compress();
					break;
				case LIST:
					Cloud.getLogger().info("o----------------------------------------------------o");
					Cloud.getLogger().info("|                     Templates:                     |");
					if (!Cloud.getInstance().getTemplateManager().getTemplates().isEmpty()) {
						Cloud.getLogger().info("|                                                    |");
					}
					Cloud.getInstance().getTemplateManager().getTemplates().forEach(template1 -> {
						spaces = calculateSpaces(51, template1.getName());
						Cloud.getLogger().info("| " + template1.getName() + spaces + "|");
					});
					Cloud.getLogger().info("o----------------------------------------------------o");
					break;
				default:
			}
		} catch (Exception ex) {
			Cloud.getLogger().error("Exception while handling template command", ex);
		}
	}

	private void sendTemplateHelp(String subcommand) {
		Cloud.getLogger().info("Template usage: template {} <name>", subcommand == null ? "<create/remove/build/compress/list>" : subcommand);
	}

	private void sendServerHelp() {
		Cloud.getLogger().info("Server usage: server <start/kill> <template/serverId>");
	}

	private void handleProfileCommand() {
		if (!Cloud.getInstance().getProfiler().isEnabled()) {
			Cloud.getLogger().info("Profiler is not enabled!");
			return;
		}
		Map<String, List<CentauriProfiler.Profile>> keyToProfiles = new HashMap<>();

		Cloud.getInstance().getProfiler().getProfiles().forEach(profile -> {
			if (!keyToProfiles.containsKey(profile.getKey()))
				keyToProfiles.put(profile.getKey(), new ArrayList<>());
			keyToProfiles.get(profile.getKey()).add(profile);
		});

		keyToProfiles.forEach((key, profiles) -> {
			final ProfilerStatistic statistic = new ProfilerStatistic();

			profiles.forEach(profile -> {
				if (profile.getTime() < statistic.getMin())
					statistic.setMin(profile.getTime());

				if (profile.getTime() > statistic.getMax())
					statistic.setMax(profile.getTime());

				statistic.setAvg(statistic.getAvg() + profile.getTime());
			});

			statistic.setAvg(statistic.getAvg() / profiles.size());

			Cloud.getLogger().info("Key: {}, Min: {}ms, Max: {}ms, Avg: {}ms", key, statistic.getMin(), statistic.getMax(), statistic.getAvg());
		});
	}

	private void handleServerCommand(String... args) {
		ServerSubcommands subCmd = Stream.of(ServerSubcommands.values())
				.filter(templateSubcommands -> templateSubcommands.command.equals(args[0]))
				.findAny()
				.orElse(null);
		if (subCmd == null) {
			this.sendServerHelp();
			return;
		}

		switch (subCmd) {
			case KILL:
				if (args.length != 2) {
					this.sendServerHelp();
					return;
				}
				Server server = Centauri.getInstance().getServer(args[1]);
				if (server == null) {
					Cloud.getLogger().warn("Cannot find server {}!", args[1]);
					return;
				}
				server.kill();
				Cloud.getLogger().info("Killed server {}!", args[1]);
				break;
			case START:
				if (args.length != 2) {
					this.sendServerHelp();
					return;
				}
				if (Centauri.getInstance().startServer(args[1]))
					Cloud.getLogger().info("Requested server with template {}!", args[1]);
				else
					Cloud.getLogger().warn("Cannot request server with template {}!", args[1]);
				break;
			default:
		}

	}

	private String calculateSpaces(int totallines, String name) {
		StringBuilder spacers = new StringBuilder();
		int numberspaces = totallines - name.length();
		for (int i = 1; i <= numberspaces; i++) {
			spacers.append(" ");
		}
		return spacers.toString();
	}


	private void handleExecuteCommand(String[] args) {
		if (args.length < 3) {
			Cloud.getLogger().info("Usage: cmd <server> <command>");
			return;
		}
		Server server = Centauri.getInstance().getServer(args[0]);
		if (server == null) {
			Cloud.getLogger().info("Server {} not found", args[0]);
			return;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < args.length; i++)
			stringBuilder.append(args[i]).append(" ");
		server.sendPacket(new PacketToServerDispatchCommand(stringBuilder.toString()));
	}

	public void handleModuleInstalling(String[] args) {
		if (args.length == 1) {
			try {
				ModuleDownloader.ModuleType type = ModuleDownloader.ModuleType.valueOf(args[0]);
				Cloud.getInstance().getModuleDownloader().download(type);
			} catch (IllegalArgumentException ex) {
				Cloud.getLogger().warn("Cannot find module!");
			}
		} else {
			Cloud.getLogger().info("Modules:");
			for (ModuleDownloader.ModuleType type : ModuleDownloader.ModuleType.values()) {
				Cloud.getLogger().info("	{}", type.getFinalName());
			}
		}
	}

	enum TemplateSubcommands {
		CREATE("create"), REMOVE("remove"), BUILD("build"), COMPRESS("compress"), LIST("list");

		private String command;

		TemplateSubcommands(String command) {
			this.command = command;
		}
	}

	enum ServerSubcommands {
		START("start"), KILL("kill");

		private String command;

		ServerSubcommands(String command) {
			this.command = command;
		}

	}

}