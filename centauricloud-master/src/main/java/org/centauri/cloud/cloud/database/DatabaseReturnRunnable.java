package org.centauri.cloud.cloud.database;

import java.sql.Connection;

@FunctionalInterface
public interface DatabaseReturnRunnable<T> {

	T execute(Connection connection) throws Exception;
}