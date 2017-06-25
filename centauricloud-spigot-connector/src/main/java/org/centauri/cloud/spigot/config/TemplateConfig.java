package org.centauri.cloud.spigot.config;

import com.timvisee.yamlwrapper.YamlConfiguration;

import java.io.File;

public class TemplateConfig extends YamlConfiguration {

	public TemplateConfig() {
		File config = new File("centauricloud.yml");
		System.out.println("Property:" + System.getProperty("user.dir"));
		System.out.println("config file: " + config.getAbsolutePath());
		try {
			this.load(config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Object getOrElse(String path, Object other) {
		Object value = this.get(path);
		if (value != null)
			return value;
		return other;
	}

}