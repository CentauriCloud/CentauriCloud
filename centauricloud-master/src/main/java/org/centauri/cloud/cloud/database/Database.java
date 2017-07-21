package org.centauri.cloud.cloud.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class Database implements AutoCloseable {

	private static Database instance;

	@Getter(onMethod = @__(@Deprecated))
	private HikariDataSource dataSource;
	private Configuration configuration;

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

	public void execVoid(DatabaseVoid runnable) {
		execVoid(runnable, true);
	}

	public void execVoid(DatabaseVoid runnable, boolean async) {
		if (async) {
			runDatabaseAction(() -> {
				try {
					runnable.execute(connection());
				} catch (Exception e) {
					throw new RuntimeException("Cannot execute runnable", e);
				}
			});
		} else {
			try {
				runnable.execute(connection());
			} catch (Exception e) {
				throw new RuntimeException("Cannot execute runnable", e);
			}
		}
	}


	public <T> T execResult(DatabaseCallback<T> runnable) {
		try {
			return runnable.execute(connection());
		} catch (Exception e) {
			throw new RuntimeException("Cannot execute runnable", e);
		}
	}


	private void connect(String user, String password, String host, int port, String database) {
		this.dataSource = new HikariDataSource();
		this.dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC");
		this.dataSource.setUsername(user);
		this.dataSource.setPassword(password);

		this.configuration = new DefaultConfiguration().set(SQLDialect.MYSQL).set(this.dataSource);
	}


	public DSLContext connection() {
		return DSL.using(configuration);
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
