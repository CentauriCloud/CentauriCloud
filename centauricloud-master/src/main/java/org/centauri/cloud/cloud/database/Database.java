package org.centauri.cloud.cloud.database;

public interface Database extends AutoCloseable {

	void connect();

	String getName();

}
