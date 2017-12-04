package org.centauri.cloud.cloud.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PSBuilder {

	private Connection connection;
	private String query;
	private List<Object> replacements = new ArrayList<>();

	private PSBuilder() {

	}

	public static PSBuilder builder() {
		return new PSBuilder();
	}

	public PSBuilder connection(Connection connection) {
		this.connection = connection;
		return this;
	}

	public PSBuilder query(String query) {
		this.query = query;
		return this;
	}

	public PSBuilder variable(Object placeholder) {
		replacements.add(placeholder);
		return this;
	}

	public PreparedStatement build() {
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			for (int i = 0; i < replacements.size(); i++) {
				ps.setObject(i + 1, replacements.get(i));
			}
			return ps;
		} catch (SQLException e) {
			throw new RuntimeException("Cannot create a PreparedStatement");
		}
	}

}
