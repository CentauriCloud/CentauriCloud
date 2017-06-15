package org.centauri.cloud.cloud.listener;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.Listener;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;
import org.centauri.cloud.cloud.profiling.ProfilerStatistic;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.template.Template;

import java.util.*;
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
			default:
				handled = false;
				break;
		}
		if (handled)
			event.setHandled(true);
	}

	private void displayHelp() {
		Cloud.getLogger().info("o-----------------------------------------------------------------------------o");
		Cloud.getLogger().info("|                               Basic Commands:                               |");
		Cloud.getLogger().info("|                                                                             |");
		Cloud.getLogger().info("| help - displays this help screen                                            |");
		Cloud.getLogger().info("| plugins (pl) - displays all plugins                                         |");
		Cloud.getLogger().info("| libraries/librarys (libs) - displays all libs                               |");
		Cloud.getLogger().info("| info - displays information about CentauriCloud and the team                |");
		Cloud.getLogger().info("| servers - displays all connected servers                                    |");
		Cloud.getLogger().info("| stop - stops the cloud                                                      |");
		Cloud.getLogger().info("| template <create/remove/build/compress/list> - some commands for templates  |");
		Cloud.getLogger().info("o-----------------------------------------------------------------------------o");
	}

	private void displayPlugins() {
		Cloud.getLogger().info("o----------------------------------------------------o");
		Cloud.getLogger().info("|                      Plugins:                      |");
		if(!Cloud.getInstance().getModuleManager().getLoaded().isEmpty()) {
			Cloud.getLogger().info("|                                                    |");
		}
		Cloud.getInstance().getModuleManager().getLoaded().forEach(pl -> {
			final StringBuilder sb = new StringBuilder();
			spaces = calculateSpaces(51, pl);
			sb.append("| ").append(pl).append(spaces).append("|");
			Cloud.getLogger().info(sb.toString());
		});
		Cloud.getLogger().info("o----------------------------------------------------o");
	}

	private void displayInfo() {
		Cloud.getLogger().info("o---------------------------------------------------------------o");
		Cloud.getLogger().info("|    CentauriCloud v1.0 developed by Centauri Developer Team    |");
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
		if(!serverTypeToServers.isEmpty()) {
			Cloud.getLogger().info("|                                                    |");
		}
		serverTypeToServers.forEach((type, servers) -> {
			spaces = calculateSpaces(51-1, type);
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
		if(!Cloud.getInstance().getLibraryLoader().getLoadedLibs().isEmpty()) {
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
					Cloud.getInstance().getTemplateManager().loadTemplate(args[1]);
					Cloud.getLogger().info("Created template {}!", args[1]);
					break;
				case REMOVE:
					if (args.length != 2) {
						sendTemplateHelp(subCmd.command);
						return;
					}
					Cloud.getInstance().getTemplateManager().removeTemplate(args[1]);
					Cloud.getLogger().info("Removed template {}!", args[1]);
					break;
				case BUILD:
					if (args.length != 2) {
						sendTemplateHelp(subCmd.command);
						return;
					}
					Template template = Cloud.getInstance().getTemplateManager().getTemplate(args[1]);
					if (template == null) {
						Cloud.getLogger().warn("Cannot find template {}!", args[1]);
						return;
					}
					template.build();
					Cloud.getLogger().info("Built template {}!", args[1]);
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
					Cloud.getLogger().info("Compressed template {}!", args[1]);
					break;
				case LIST:
					final StringBuilder sb = new StringBuilder();
					Cloud.getLogger().info("o----------------------------------------------------o");
					Cloud.getLogger().info("|                     Templates:                     |");
					if(!Cloud.getInstance().getTemplateManager().getTemplates().isEmpty()) {
						Cloud.getLogger().info("|                                                    |");
					}
					Cloud.getInstance().getTemplateManager().getTemplates().forEach(template1 -> {
						spaces = calculateSpaces(51, template1.getName());
						Cloud.getLogger().info("| " + template1.getName() + spaces + "|");
					});
					Cloud.getLogger().info("o----------------------------------------------------o");
					break;
			}
		} catch (Exception ex) {
			Cloud.getLogger().error("Exception while handling template command", ex);
		}
	}

	private void sendTemplateHelp(String subcommand) {
		Cloud.getLogger().info("Template usage: template {} <name>", subcommand == null ? "<create/remove/build/compress/list>" : subcommand);
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

	private String calculateSpaces(int totallines, String name) {
		StringBuilder spaces = new StringBuilder();
		int numberspaces = totallines - name.length();
		for (int i = 1; i <= numberspaces; i++) {
			spaces.append(" ");
		}
		return spaces.toString();
	}

	enum TemplateSubcommands {
		CREATE("create"), REMOVE("remove"), BUILD("build"), COMPRESS("compress"), LIST("list");

		private String command;

		TemplateSubcommands(String command) {
			this.command = command;
		}
	}

}