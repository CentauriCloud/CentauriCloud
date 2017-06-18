package org.centauri.cloud.cloud.database;

import org.centauri.cloud.cloud.player.Player;

public abstract class DatabaseAPI {
	
	protected abstract void connect(String user, String password, String host, int port, String database) throws Exception;
	
	protected abstract void disconnect();
	
	public abstract String getDatabaseType();
	
	public abstract String[] getSupportedVersion();
	
	public abstract void savePlayerData(Player player);
	
	public abstract Player loadPlayerData(Player player);
	
}