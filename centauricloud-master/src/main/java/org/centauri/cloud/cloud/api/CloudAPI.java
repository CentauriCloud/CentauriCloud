package org.centauri.cloud.cloud.api;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.api.manager.LibraryManager;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.template.TemplateManager;

public class CloudAPI {

	@Getter private static CloudAPI instance = new CloudAPI();
	
	@Getter private LibraryManager libraryManager = new LibraryManager();
	
	public Set<Server> getServers(String prefix) {
		return Cloud.getInstance().getServerManager().getChannelToServer().values().stream().filter(server -> server.getPrefix().equals(prefix)).collect(Collectors.toSet());
	}
	
	public Server getServer(String name) {
		return Cloud.getInstance().getServerManager().getNameToServer().get(name);
	}
	
	public EventManager getEventManager() {
		return Cloud.getInstance().getEventManager();
	}
	
	public TemplateManager getTemplateManager() {
		return Cloud.getInstance().getTemplateManager();
	}
}