package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.network.packets.PacketStartServer;
import org.centauri.cloud.cloud.network.packets.PacketTemplateData;
import org.centauri.cloud.cloud.template.Template;

public class Daemon extends Server {
	
	@Getter @Setter private List<Server> servers = Collections.synchronizedList(new ArrayList<Server>());
	
	public Daemon(Channel channel) {
		super(channel);
		this.sendTemplates();
	}
	
	private void sendTemplates() {
		Cloud.getInstance().getTemplateManager().getTemplates().forEach(template -> {
			this.sendTemplate(template);
		});
	}
	
	@SneakyThrows
	private void sendTemplate(Template template) {
		String path = PropertyManager.getInstance().getProperties().getProperty("tmpDir", "tmp/") + template.getName() + ".zip";
		FileInputStream inputStream = new FileInputStream(path);
		byte[] data = new byte[inputStream.available()];
		System.out.println("Debug: read data...");
		inputStream.read(data);
		System.out.println("Debug: read successfully template data: " + template.getName() + " Size: " + data.length);
		this.getChannel().writeAndFlush(new PacketTemplateData(data));
	}
	
	public void startServer(String prefix) {
		this.getChannel().writeAndFlush(new PacketStartServer(prefix));
	}
	
}
