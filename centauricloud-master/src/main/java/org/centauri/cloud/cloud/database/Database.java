package org.centauri.cloud.cloud.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;

public class Database {
	
	@Getter private DatabaseType type;
	
	//MongoDB
	@Getter private MongoClient mongoClient;
	@Getter private MongoDatabase mongoDatabase;
	
	//SQL
	@Getter private HikariDataSource hikari;
	
	public void connect(String user, String password, String host, int port, String database, DatabaseType type) {
		this.type = type;
		
		try {
			if(type == DatabaseType.MonogDB) {
				this.connectToMongoDB(user, password, host, port, database);
			} else {
				this.connectToSQL(user, password, host, port, database);
			}
		} catch (Exception ex) {
			Cloud.getLogger().catching(ex);
		}
	}
	
	public void disconnect() {
		try {
			if(this.mongoClient != null)
				this.mongoClient.close();
			if(this.hikari != null)
				this.hikari.close();
		} catch (Exception ex) {
			Cloud.getLogger().catching(ex);
		}
	}
	
	private void connectToMongoDB(String user, String password, String host, int port, String database) throws Exception {
		List<MongoCredential> credentials = Arrays.asList(MongoCredential.createCredential(user, database, password.toCharArray()));
		this.mongoClient = new MongoClient(new ServerAddress(host, port), credentials);
		this.mongoDatabase = mongoClient.getDatabase(database);
	}
	
	private void connectToSQL(String user, String password, String host, int port, String database) throws Exception {
		this.hikari = new HikariDataSource();
        this.hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.hikari.addDataSourceProperty("serverName", host);
        this.hikari.addDataSourceProperty("port", port);
        this.hikari.addDataSourceProperty("databaseName", database);
        this.hikari.addDataSourceProperty("user", user);
        this.hikari.addDataSourceProperty("password", password);
	}

}