package org.centauri.cloud.cloud.config;

import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public final class PropertyManager {

	private static PropertyManager instance = getInstance();

	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
			instance.load();
		}
		return instance;
	}

	@Getter private Properties properties;

	private PropertyManager() {

	}

	public void initVariables(Cloud cloud) {
		cloud.setTimeout(Integer.parseInt(properties.getProperty("timeout")));
		cloud.setPingerIntervall(Integer.valueOf((String) properties.get("pingerIntervall")));
		cloud.setWhitelistActivated(Boolean.valueOf(properties.getProperty("whitelist")));
		cloud.setPort(Integer.valueOf(properties.getProperty("port")));
		cloud.setSharedDir(new File(properties.getProperty("sharedDir", "shared/")));
		cloud.setTemplatesDir(new File(PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/")));
		cloud.setTmpDir(new File(PropertyManager.getInstance().getProperties().getProperty("tmpDir", "tmp/")));
		cloud.setLibDir(new File(PropertyManager.getInstance().getProperties().getProperty("libDir", "libs/")));
	}

	private void load() {
		File file = new File("config.properties");
		if (!file.exists()) {
			createFile(file);
		}
		try (InputStream inputStream = new FileInputStream(file)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			Cloud.getLogger().error(e.getMessage(), e);
		}
	}

	private void createFile(File out) {
		try {
			Files.copy(this.getClass().getResourceAsStream("/config.properties"), out.toPath());
		} catch (IOException e) {
			Cloud.getLogger().error(e.getMessage(), e);
		}

	}
}
