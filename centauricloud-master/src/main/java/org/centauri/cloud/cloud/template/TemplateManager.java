package org.centauri.cloud.cloud.template;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.server.Daemon;

public class TemplateManager {
	
	@Getter private Set<Template> templates = new HashSet<>();
	
	public TemplateManager() {
		new File(PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/")).mkdir();
		try {
			File tmp = new File(PropertyManager.getInstance().getProperties().getProperty("tmpDir", "tmp/"));
			FileUtils.deleteDirectory(tmp);
			tmp.mkdir();
		} catch (Exception ex) {
			Cloud.getLogger().catching(ex);
		}
	}
	
	public void removeTemplate(String name) throws Exception {
		Template template = this.getTemplate(name);
		if(template == null) {
			Cloud.getLogger().warn("Cannot find module {}!", name);
			return;
		}
		FileUtils.deleteDirectory(template.getDir());
	}
	
	public Template loadTemplate(String name) {
		Template template = new Template(name, new File(PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/") + name + "/"));
		this.templates.add(template);
		return template;
	}
	
	public Template createTemplate(String name) throws Exception {
		Template template = this.loadTemplate(name);
		template.getDir().mkdir();
		File config = new File(template.getDir().getPath(), "centauricloud.properties");
		Files.copy(this.getClass().getResourceAsStream("/centauricloud.properties"), config.toPath());
		return template;
	}
	
	public Template getTemplate(String name) {
		return templates.stream().filter(template -> template.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public void sendTemplate(Daemon daemon) {
		//TODO
	}

}