package org.centauri.cloud.cloud.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
		if (server == null || cmd.isEmpty()) {
			return false;
		}
		server.sendPacket(new PacketToServerDispatchCommand(cmd));
		return true;
	}

	public String getCloudVersion() {
		return "1.0";
	}

	public List<String> getConfigFromTemplate(String templatename) {
        try {
            Template template = Cloud.getInstance().getTemplateManager().getTemplate(templatename);
            if(template == null) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new FileReader(template.getConfig()));
            List<String> lines;
            try {
                lines = reader.lines().collect(Collectors.toList());
            } finally {
                reader.close();
            }
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getConfigFromServer(String servername) {
        Server server = this.getServer(servername);
        //TODO
        return null;
    }

	public List<String> getFileContent(String path) {
        try {
            File file = new File(path);
            if(!file.exists()) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines;
            try {
                lines = reader.lines().collect(Collectors.toList());
            } finally {
                reader.close();
            }
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setFileContent(String path, List<String> content) {
        try {
            File file = new File(path);
            if(!file.exists() || content.isEmpty()) {
                return false;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            try {
                for (String aContent : content) {
                    writer.write(aContent);
                }
            } finally {
                writer.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public EventManager getEventManager() {
		return Cloud.getInstance().getEventManager();
	}
	
	public TemplateManager getTemplateManager() {
		return Cloud.getInstance().getTemplateManager();
	}
}