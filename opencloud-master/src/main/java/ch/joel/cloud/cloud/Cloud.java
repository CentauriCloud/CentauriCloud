package ch.joel.cloud.cloud;

import ch.joel.cloud.cloud.config.PropertyManager;

public class Cloud {

	public static void main(String[] args) {
		PropertyManager manager = new PropertyManager();
		manager.load();
		ModuleLoader loader = new ModuleLoader();
		loader.initializeScheduler();


	}


}
