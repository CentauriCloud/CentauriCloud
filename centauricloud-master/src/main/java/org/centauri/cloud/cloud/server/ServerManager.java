package org.centauri.cloud.cloud.server;

import io.netty.channel.Channel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.ServerConnectEvent;
import org.centauri.cloud.cloud.event.events.ServerDisconnectEvent;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.common.network.util.Callback;

public class ServerManager {

	private final Map<Channel, Server> channelToServer = new HashMap<>();
	private final ReadWriteLock channelToServerLock = new ReentrantReadWriteLock(true);

	private final Map<String, Server> nameToServer = new HashMap<>();
	private final ReadWriteLock nameToServerLock = new ReentrantReadWriteLock(true);

	public void add(Server server) {
		if(server == null)
			return;

		//channelToServer
		this.channelToServerLock.writeLock().lock();
		try {
			this.channelToServer.put(server.getChannel(), server);
			server.setId(this.getId(server.getPrefix()));
			server.setName(server.getPrefix() + "-" + server.getId());
		} finally {
			this.channelToServerLock.writeLock().unlock();
		}

		//nameToServer
		this.nameToServerLock.writeLock().lock();
		try {
			this.nameToServer.put(server.getName(), server);
		} finally {
			this.nameToServerLock.writeLock().unlock();
		}

		Cloud.getInstance().getEventManager().callEvent(new ServerConnectEvent(server));
	}

	public void remove(Channel channel) {
		this.remove(this.get(channel));
	}

	public void remove(Server server) {
		if(server == null)
			return;

		//channelToServer
		this.channelToServerLock.writeLock().lock();
		try {
			this.channelToServer.remove(server.getChannel());
		} finally {
			this.channelToServerLock.writeLock().unlock();
		}

		//nameToServer
		this.nameToServerLock.writeLock().lock();
		try {
			this.nameToServer.remove(server.getName());
		} finally {
			this.nameToServerLock.writeLock().unlock();
		}

		//finally call a ServerDisconnectEvent and kill all child-servers
		Cloud.getInstance().getEventManager().callEvent(new ServerDisconnectEvent(server));

		if (server instanceof Daemon) {
			Daemon daemon = (Daemon) server;
			this.stream(stream -> {
				stream.filter(server1 -> server1.getHost().equals(daemon.getHost())).forEach(childServer -> {
					childServer.kill();
				});
			});
		}
	}

	/**
	 * Returns the server or null.
	 * 
	 * Note: This method is synchronized(blocking)!
	 * 
	 * @param channel
	 * @return the server or null
	 */
	public Server get(Channel channel) {
		Server server = null;
		this.channelToServerLock.readLock().lock();

		try {
			server = this.channelToServer.get(channel);
		} finally {
			this.channelToServerLock.readLock().unlock();
		}
		
		return server;
	}

	/**
	 * Returns the server or null.
	 * 
	 * Note: This method is synchronized(blocking)!
	 * 
	 * @param name
	 * @return the server or null
	 */
	public Server get(String name) {
		Server server = null;
		this.nameToServerLock.readLock().lock();

		try {
			server = this.nameToServer.get(name);
		} finally {
			this.nameToServerLock.readLock().unlock();
		}

		return server;
	}

	/**
	 * Generates a synchronized stream and calls the callback with this stream
	 * as a parameter.
	 * After calling the callback, the lock will be removed.
	 * 
	 * Note: This method is synchronized(blocking)!
	 * 
	 * @param callback 
	 */
	public void stream(final Callback<Stream<Server>> callback) {
		this.channelToServerLock.readLock().lock();

		try {
			final Stream<Server> stream = this.channelToServer.values().stream();
			callback.call(stream);
		} finally {
			this.channelToServerLock.readLock().unlock();
		}

	}

	private int getId(final String prefix) {
		Collection<Server> serversWithPrefix = Centauri.getInstance().getServers(prefix);

		for (int i = 1; i < Integer.MAX_VALUE; i++)
			if (!this.isIdUsed(i, serversWithPrefix))
				return i;

		return -1337;
	}

	private boolean isIdUsed(int id, Collection<Server> serversWithPrefix) {
		for (Server server : serversWithPrefix)
			if (server.getId() == id)
				return true;
		return false;
	}
}
