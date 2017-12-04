package org.centauri.cloud.cloud.database;

import org.jooq.DSLContext;

@FunctionalInterface
public interface DatabaseCallback<T> {

	T execute(DSLContext context) throws Exception;
}