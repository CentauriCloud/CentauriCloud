package org.centauri.cloud.daemon.server;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

public class ServerManager {

	@Getter private Set<Template> templates = new HashSet<>();
	
	public void startServer(String templateName) {
		this.templates.forEach(template -> {
			if(template.getName().equals(templateName)) {
				File copy = template.createCopy();
				//TODO: Start spigot.jar
				System.out.println("Started new server from template: " + templateName);
			}
		});
	}
	
}