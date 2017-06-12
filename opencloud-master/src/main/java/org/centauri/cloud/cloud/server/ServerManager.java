package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.ServerConnectEvent;
import org.centauri.cloud.cloud.event.events.ServerDisconnectEvent;

public class ServerManager {
	
	@Getter private ConcurrentMap<Channel, Server> channelToServer = new ConcurrentHashMap<>();
	@Getter private ConcurrentMap<String, Server> nameToServer = new ConcurrentHashMap<>();

	public void registerServer(Server server) {
		this.channelToServer.put(server.getChannel(), server);
		this.nameToServer.put(server.getName(), server);
		Cloud.getInstance().getEventManager().callEvent(new ServerConnectEvent(server));
	}
	
	public void removeServer(Channel channel) {
		Server server = this.channelToServer.get(channel);
		this.channelToServer.remove(channel);
		this.nameToServer.remove(server.getName());
		Cloud.getInstance().getEventManager().callEvent(new ServerDisconnectEvent(server));
	}
	
}
