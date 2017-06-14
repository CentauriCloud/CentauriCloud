package org.centauri.cloud.cloud.api;

import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.api.manager.LibraryManager;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.template.TemplateManager;

public class CloudAPI {

	@Getter private static CloudAPI instance = new CloudAPI();
	
	@Getter private LibraryManager libraryManager = new LibraryManager();
	
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