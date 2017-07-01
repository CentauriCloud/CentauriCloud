package org.centauri.cloud.cloud.install;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Properties;
import java.util.Scanner;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.install.installer.DirectoryInstaller;

public class Installer {

	public void start() {
		File configFile = new File("config.properties");

		//Check if install is needed
		if(configFile.exists())
			return;

		createFile(configFile);

		//Load config
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(configFile)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			Cloud.getLogger().error(e.getMessage(), e);
		}
		Scanner scanner = new Scanner(System.in);

		try (Writer writer = new FileWriter(configFile);) {

			new DirectoryInstaller().start(scanner, properties);

			properties.store(writer, null);
			writer.flush();
		} catch(IOException ex) {
			Cloud.getLogger().error(ex.getMessage(), ex);
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