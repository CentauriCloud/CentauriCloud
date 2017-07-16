package org.centauri.cloud.cloud.module;

import org.centauri.cloud.cloud.config.Config;

import java.io.File;

public abstract class AbstractModule extends Config implements Module {

	private File file = new File(getConfig("modulesDir") + getName() + "/");

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}

	@Override
	public File getModuleDirectory() {
		file.mkdir();
		return file;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Module))
			return false;
		Module module = (Module) obj;
		return getName().equals(module.getName())
				&& getVersion().equals(module.getVersion())
				&& getAuthor().equals(module.getAuthor());
	}
}
