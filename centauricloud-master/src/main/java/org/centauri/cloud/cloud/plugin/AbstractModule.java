package org.centauri.cloud.cloud.plugin;

public abstract class AbstractModule implements Module {

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Module))
			return false;
		Module module = (Module) obj;
		return getName().equals(module.getName()) && getVersion().equals(module.getVersion()) && getAuthor().equals(module.getAuthor());
	}
}
