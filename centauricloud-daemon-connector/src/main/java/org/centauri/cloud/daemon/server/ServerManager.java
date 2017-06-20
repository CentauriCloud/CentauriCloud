package org.centauri.cloud.daemon.server;

import lombok.Getter;
import lombok.SneakyThrows;
import org.centauri.cloud.common.network.util.PortChecker;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerManager {

	@Getter private Set<Template> templates = new HashSet<>();
	@Getter private final List<Process> serverProcesses = new ArrayList<>();

	private AtomicInteger integer = new AtomicInteger(11 * 1000);

	@SneakyThrows
	public void startServer(String templateName) {
		Template template = this.templates.stream().filter(tmp -> tmp.getName().equals(templateName))
				.findAny().orElse(null);
		if (template == null)
			return;

		File copy = template.createCopy();
		Properties config = new Properties();
		config.load(new FileInputStream(copy.getPath() + "/centauricloud.properties"));
		ProcessBuilder builder = new ProcessBuilder(
				config.getProperty("startCommand").replaceAll("%port%", String.valueOf(this.getNextPort())).split(" ")
		);
		builder.directory(copy);
		Process process = builder.start();
		this.serverProcesses.add(process);
		System.out.println("Started new server from template: " + templateName);
	}

	private int getNextPort() {
		int nextInt;
		do {
			nextInt = integer.getAndIncrement();
		} while (!PortChecker.available(nextInt));
		return nextInt;
	}

}