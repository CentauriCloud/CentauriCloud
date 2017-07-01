package org.centauri.cloud.cloud.install.installer;

import java.util.Properties;
import java.util.Scanner;
import org.centauri.cloud.cloud.Cloud;

public class ExtendedInstaller {

	public void start(Scanner scanner, Properties config) {
		boolean change = false;

		Cloud.getLogger().info("Do you want change extended options? Type: true or false");
		change = Boolean.valueOf(scanner.nextLine());
		if(change) {
			Cloud.getLogger().info("Type the number(no floating points!). Default: 8012");
			config.setProperty("port", scanner.nextLine());
		}

	}

}