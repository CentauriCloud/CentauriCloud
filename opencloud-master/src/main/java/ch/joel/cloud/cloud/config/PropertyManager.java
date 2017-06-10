package ch.joel.cloud.cloud.config;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class PropertyManager {

	@Getter private static PropertyManager instance;
	@Getter private Properties properties;

	public PropertyManager() {
		instance = this;
	}

	public void load() {
		File file = new File("config.properties");
		if (!file.exists())
			createFile(file);
		try (InputStream inputStream = new FileInputStream(file)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createFile(File out) {
		try {
			Files.copy(this.getClass().getResourceAsStream("/config.properties"), out.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
