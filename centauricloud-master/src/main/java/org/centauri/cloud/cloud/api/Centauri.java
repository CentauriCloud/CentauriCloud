package org.centauri.cloud.cloud.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.api.manager.LibraryManager;
import org.centauri.cloud.cloud.event.EventManager;
import org.centauri.cloud.cloud.event.events.RequestServerEvent;
import org.centauri.cloud.cloud.server.Daemon;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.template.Template;
import org.centauri.cloud.cloud.template.TemplateManager;
import org.centauri.cloud.common.network.packets.PacketToServerDispatchCommand;

public class Centauri {

	@Getter private static Centauri instance = new Centauri();
	
	@Getter private LibraryManager libraryManager = new LibraryManager();
	
	public Collection<Server> getServers() {
		return Cloud.getInstance().getServerManager().getChannelToServer().values();
	}
	
	public Set<Server> getServers(String prefix) {
		return Cloud.getInstance().getServerManager().getChannelToServer().values().stream().filter(server -> server.getPrefix().equals(prefix)).collect(Collectors.toSet());
	}
	
	public Server getServer(String name) {
		return Cloud.getInstance().getServerManager().getNameToServer().get(name);
	}
	
	public boolean startServer(String templateName) {
		List<Server> daemons = Cloud.getInstance().getServerManager().getChannelToServer().values().stream().filter(server -> server instanceof Daemon).collect(Collectors.toList());
		if (daemons.isEmpty())
			return false;
		Daemon daemon = (Daemon) daemons.get(0);
		Template template = Cloud.getInstance().getTemplateManager().getTemplate(templateName);
		if(template == null)
			return false;
		daemon.startServer(templateName);
		Cloud.getInstance().getEventManager().callEvent(new RequestServerEvent(template));
		return true;
	}

	public boolean sendCommandToServer(String cmd, String servername) {
		Server server = this.getServer(servername);
		if (server == null) {
			return false;
		}
		server.sendPacket(new PacketToServerDispatchCommand(cmd));
		return true;
	}
	
	public EventManager getEventManager() {
		return Cloud.getInstance().getEventManager();
	}
	
	public TemplateManager getTemplateManager() {
		return Cloud.getInstance().getTemplateManager();
	}
}