package org.centauri.cloud.cloud.module;

import java.io.File;

public interface Module {

	String getName();

	String getVersion();

	String getAuthor();

	void onEnable();

	void onDisable();

	File getModuleDirectory();

}
