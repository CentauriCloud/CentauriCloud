package org.centauri.cloud.cloud.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;
import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;

public class PropertyManager {

	@Getter private static PropertyManager instance;
	@Getter private Properties properties;

	public PropertyManager() {
		if (instance == null)
			instance = this;
	}

	public void initVariables(Cloud cloud) {
		cloud.setTimeout(Integer.valueOf(properties.getProperty("timeout")));
		cloud.setPingerIntervall(Integer.valueOf((String) properties.get("pingerIntervall")));
		cloud.setWhitelistActivated(Boolean.valueOf((String) properties.getProperty("whitelist")));
		cloud.setPort(Integer.valueOf(properties.getProperty("port")));
		cloud.setSharedDir(new File(properties.getProperty("sharedDir", "shared/")));
		cloud.setTemplatesDir(new File(PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/")));
		cloud.setTmpDir(new File(PropertyManager.getInstance().getProperties().getProperty("tmpDir", "tmp/")));
		cloud.setLibDir(new File(PropertyManager.getInstance().getProperties().getProperty("libDir", "libs/")));
	}

	public void load() {
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
