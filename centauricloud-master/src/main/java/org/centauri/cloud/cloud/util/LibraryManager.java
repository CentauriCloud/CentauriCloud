package org.centauri.cloud.cloud.util;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;

import java.io.File;

public class LibraryManager {

	public void downloadLib(String url, String outputName) throws Exception {
		Cloud.getInstance().getLibraryDownloader().downloadLib(url, PropertyManager.getInstance().getProperties().getProperty("libDir", "libs/"), outputName);
	}

	public void loadLib(File lib, ClassLoader pluginClassLoader) throws Exception {
		Cloud.getInstance().getLibraryLoader().loadLib(lib.toURI().toURL(), pluginClassLoader);
	}
}