package org.centauri.cloud.cloud.download.mongodb;

import org.centauri.cloud.cloud.database.Database;

public class MongoDB implements Database {

	@Override
	public void connect() {
	}

	@Override
	public void close() throws Exception {
	}

	@Override
	public String getName() {
		return "MongoDB";
	}

}
