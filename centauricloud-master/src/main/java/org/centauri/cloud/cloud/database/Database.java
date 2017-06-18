package org.centauri.cloud.cloud.database;

import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.Cloud;

public class Database {
	
	@Getter private static Database instance;
	@Getter @Setter private DatabaseAPI databaseAPI;
	
	public Database() {
		instance = this;
	}
	
	public void connect(String user, String password, String host, int port, String database) {
		if(this.databaseAPI == null) {
			Cloud.getLogger().warn("No database module found!");
			return;
		}
		
		try {
			this.databaseAPI.connect(user, password, host, port, database);
		} catch (Exception ex) {
			Cloud.getLogger().catching(ex);
		}
	}
	
	public void disconnect() {
		if(this.databaseAPI == null) {
			Cloud.getLogger().warn("No database module found!");
			return;
		}
		
		this.databaseAPI.disconnect();
	}

}