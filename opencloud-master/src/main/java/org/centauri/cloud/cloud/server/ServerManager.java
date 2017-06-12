package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.Getter;

public class ServerManager {
	
	@Getter private ConcurrentMap<Channel, Server> channelToServer = new ConcurrentHashMap<>();
	@Getter private ConcurrentMap<String, Server> nameToServer = new ConcurrentHashMap<>();

}
