package org.centauri.cloud.cloud;

import org.centauri.cloud.cloud.config.PropertyManager;

public class Cloud {

	public static void main(String[] args) {
		PropertyManager manager = new PropertyManager();
		manager.load();
		ModuleLoader loader = new ModuleLoader();
		loader.initializeScheduler();


	}


}
