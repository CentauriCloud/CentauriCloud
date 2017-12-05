package org.centauri.cloud.cloud.install.installer;

import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.download.ModuleDownloader;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class ModuleInstaller {

	public void start(Scanner scanner, Properties config) {
		boolean change = false;

		Cloud.getLogger().info("Do you want to download some modules? Type: true or false");
		moduleLoop:
		while (scanner.hasNextLine()) {
			String input = scanner.nextLine();
			String[] args = input.substring(input.split(" ")[0].length()).trim().split(" ");

			System.out.println(Arrays.deepToString(args));

			switch (input.split(" ")[0]) {
				case "install":
					if (args.length == 0) {
						Cloud.getLogger().info("Modules: ");
						for (ModuleDownloader.ModuleType type : ModuleDownloader.ModuleType.values()) {
							Cloud.getLogger().info("	{}", type.toString());
						}
					} else {
						try {
							ModuleDownloader.ModuleType type = ModuleDownloader.ModuleType.valueOf(args[0].toUpperCase());
							Cloud.getInstance().getModuleDownloader().download(type);
						} catch (IllegalArgumentException ex) {
							Cloud.getLogger().warn("Cannot find module: {}", args[0]);
						}
					}

					break;
				case "break":
				case "exit":
					break moduleLoop;
				default:
					Cloud.getLogger().info("Commands:");
					Cloud.getLogger().info("install <module> - download and install an module from the official repo");
					Cloud.getLogger().info("exit - stop the module installer and continue with the normal installer");

			}
		}

	}

}