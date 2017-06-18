package org.centauri.cloud.cloud.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		return Cloud.getInstance().getVersion();
	}

	public List<String> getConfigFromTemplate(String templatename) {
        Template template = Cloud.getInstance().getTemplateManager().getTemplate(templatename);
        if(template == null) {
            return null;
        }
        File file = template.getConfig();
        return getFileContent(file.getPath());
    }

	public List<String> getFileContent(String path) {
        try {
            File file = new File(path);
            if(!file.exists()) {
                return null;
            }
            List<String> lines;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                lines = reader.lines().collect(Collectors.toList());
            }
            return lines;
        } catch (Exception e) {
            Cloud.getLogger().error(e.getMessage(), e);
        }
        return null;
    }

    public boolean setConfigFromTemplate(String templatename, List<String> content) {
        Template template = Cloud.getInstance().getTemplateManager().getTemplate(templatename);
        if(template == null) {
            return false;
        }
        File file = template.getConfig();
        return setFileContent(file.getPath(), content);
    }

    public boolean setFileContent(String path, List<String> content) {
        try {
            File file = new File(path);
            if(!file.exists() || content.isEmpty()) {
                return false;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String aContent : content) {
                    writer.write(aContent + "\n");
                }
            }
            return true;
        } catch (Exception e) {
            Cloud.getLogger().error(e.getMessage(), e);
        }
        return false;
    }

    public List<File> getDirContent(String path) {
        File dir = new File(path);
        if(!dir.isDirectory()) {
            return null;
        }
        return Arrays.asList(dir.listFiles());
    }
	
	public EventManager getEventManager() {
		return Cloud.getInstance().getEventManager();
	}
	
	public TemplateManager getTemplateManager() {
		return Cloud.getInstance().getTemplateManager();
	}
}