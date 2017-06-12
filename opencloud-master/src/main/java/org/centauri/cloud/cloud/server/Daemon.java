package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Daemon extends Server {
	
	@Getter @Setter private List<Server> servers = Collections.synchronizedList(new ArrayList<Server>());
	
	public Daemon(Channel channel) {
		super(channel);
	}
	
	public void startServer(String prefix) {
		
	}
	
}
