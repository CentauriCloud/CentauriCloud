package org.centauri.cloud.common.network.config;

import com.timvisee.yamlwrapper.YamlConfiguration;

import java.io.File;

public class TemplateConfig extends YamlConfiguration {

	public TemplateConfig(File templateDir) {
		File config = new File(templateDir.getPath() + "/" + "centauricloud.yml");

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