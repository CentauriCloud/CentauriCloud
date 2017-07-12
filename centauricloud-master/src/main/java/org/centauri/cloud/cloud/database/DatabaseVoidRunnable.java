package org.centauri.cloud.cloud.database;

import java.sql.Connection;

@FunctionalInterface
public interface DatabaseVoidRunnable {

	void execute(Connection connection) throws Exception;
}