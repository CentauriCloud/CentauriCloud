package org.centauri.cloud.cloud.install.installer;

import org.centauri.cloud.cloud.Cloud;

import java.util.Properties;
import java.util.Scanner;

public class DirectoryInstaller {

	public void start(Scanner scanner, Properties config) {
		boolean change = false;

		Cloud.getLogger().info("Do you want change the modules dir? Type: true or false");
		change = Boolean.valueOf(scanner.nextLine());
		if (change) {
			Cloud.getLogger().info("Type the relative path. Default: modules/");
			config.setProperty("modulesDir", scanner.nextLine());
		}

		Cloud.getLogger().info("Do you want change the libraries dir? Type: true or false");
		change = Boolean.valueOf(scanner.nextLine());
		if (change) {
			Cloud.getLogger().info("Type the relative path. Default: libs/");
			config.setProperty("libDir", scanner.nextLine());
		}

		Cloud.getLogger().info("Do you want change the templates dir? Type: true or false");
		change = Boolean.valueOf(scanner.nextLine());
		if (change) {
			Cloud.getLogger().info("Type the relative path. Default: templates/");
			config.setProperty("templatesDir", scanner.nextLine());
		}

		Cloud.getLogger().info("Do you want change the shared dir? Type: true or false");
		change = Boolean.valueOf(scanner.nextLine());
		if (change) {
			Cloud.getLogger().info("Type the relative path. Default: shared/");
			config.setProperty("sharedDir", scanner.nextLine());
		}

		Cloud.getLogger().info("Do you want change the temporary dir? Type: true or false");
		change = Boolean.valueOf(scanner.nextLine());
		if (change) {
			Cloud.getLogger().info("Type the relative path. Default: tmp/");
			config.setProperty("tmpDir", scanner.nextLine());
		}

	}

}