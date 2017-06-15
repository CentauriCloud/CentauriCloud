package org.centauri.cloud.daemon.server;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import lombok.Getter;
import lombok.SneakyThrows;

public class ServerManager {

	@Getter private Set<Template> templates = new HashSet<>();
	private final List<Process> serverProcesses = new ArrayList<>();
	
	@SneakyThrows
	public void startServer(String templateName) {
		this.templates.forEach(template -> {
			try {
				if (template.getName().equals(templateName)) {
					File copy = template.createCopy();
					Properties config = new Properties();
					config.load(new FileInputStream(copy.getPath() + "/centauricloud.properties"));
					ProcessBuilder builder = new ProcessBuilder(
							config.getProperty("startCommand").split(" ")
					);
					builder.directory(copy);
					Process process = builder.start();
					this.serverProcesses.add(process);
					System.out.println("Started new server from template: " + templateName);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
}