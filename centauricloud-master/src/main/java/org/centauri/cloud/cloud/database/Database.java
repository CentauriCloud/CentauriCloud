package org.centauri.cloud.cloud.database;

import com.zaxxer.hikari.HikariDataSource;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class Database implements AutoCloseable {

	private static Database instance = getInstance();

	private HikariDataSource dataSource;
	private Thread databaseThread;
	private final ExecutorService queue;

	private Database() {
		ThreadFactory threadBuilder = (Runnable r) -> {
			Thread thread = new Thread(r, "Database Queue");
			thread.setDaemon(true);
			this.databaseThread = thread;
			return thread;
		};
		this.queue = Executors.newSingleThreadExecutor(threadBuilder);

	}

	public void execVoid(DatabaseVoidRunnable runnable) {
		execVoid(runnable, true);
	}

	public void execVoid(DatabaseVoidRunnable runnable, boolean async) {
		Connection connection;
		connection = getConnection();
		if (async) {
			runDatabaseAction(() -> {
				try {
					runnable.execute(connection);
					connection.close();
				} catch (Exception e) {
					Cloud.getLogger().catching(e);
				}
			});
		} else {
			try {
				runnable.execute(connection);
				connection.close();
			} catch (Exception e) {
				Cloud.getLogger().catching(e);
			}
		}
	}


	public <T> T execResult(DatabaseReturnRunnable<T> runnable) {
		Connection connection = null;
		try {
			connection = getConnection();
			return runnable.execute(connection);
		} catch (Exception e) {
			Cloud.getLogger().catching(e);
			return null;
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				Cloud.getLogger().catching(e);
			}
		}
	}


	private void connect(String user, String password, String host, int port, String database) {
		this.dataSource = new HikariDataSource();
		this.dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC");
		this.dataSource.setUsername(user);
		this.dataSource.setPassword(password);
	}


	private Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get Connection", e);
		}
	}

	/**
	 * Just use this method if your absolutely done with the whole program
	 *
	 * @throws Exception tries to close the whole dataSource
	 */
	@Override
	public void close() throws Exception {
		dataSource.close();
	}

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
			String username = PropertyManager.getInstance().getProperties().getProperty("dbUser");
			String password = PropertyManager.getInstance().getProperties().getProperty("dbPassword");
			String host = PropertyManager.getInstance().getProperties().getProperty("dbHost");
			int port = Integer.valueOf(PropertyManager.getInstance().getProperties().getProperty("dbPort"));
			String database = PropertyManager.getInstance().getProperties().getProperty("dbName");
			instance.connect(username, password, host, port, database);
		}
		return instance;
	}


	private void runDatabaseAction(final Runnable action) {
		if (this.isDatabaseThread()) {
			action.run();
		} else {
			this.queue.execute(() -> {
				try {
					action.run();
				} catch (Exception ex) {
					Cloud.getLogger().error("Unhandled exception while database execution", ex);
				}
			});
		}
	}

	private boolean isDatabaseThread() {
		return Thread.currentThread() == this.databaseThread;
	}

}
