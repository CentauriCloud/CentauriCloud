package org.centauri.cloud.cloud.database;

import org.jooq.DSLContext;

@FunctionalInterface
public interface DatabaseVoid {

	void execute(DSLContext context) throws Exception;
}