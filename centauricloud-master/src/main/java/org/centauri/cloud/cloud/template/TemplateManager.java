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
		this.createDefaultDirectories();
	}
	
	public void removeTemplate(String name) throws Exception {
		Template template = this.getTemplate(name);
		if(template == null) {
			Cloud.getLogger().warn("Cannot find module {}!", name);
			return;
		}
		
		template.getPropertiesInputStream().close();
		FileUtils.deleteDirectory(template.getDir());
	}
	
	public Template loadTemplate(String name) throws Exception {
		String templatesDirPath = PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/") + name + "/";
		
		Template template = new Template(name, new File(templatesDirPath), new File(templatesDirPath, "centauricloud.properties"));
		template.getDir().mkdir();
		
		if(!template.getConfig().exists())
			Files.copy(this.getClass().getResourceAsStream("/centauricloud.properties"), template.getConfig().toPath());
		
		template.loadConfig();
		template.loadSharedFiles();
		
		this.templates.add(template);
		return template;
	}
	
	public Template getTemplate(String name) {
		return templates.stream().filter(template -> template.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public void sendTemplate(Daemon daemon) {
		//TODO
	}
	
	private void createDefaultDirectories() {
		try {
			new File(PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/")).mkdir();
			
			File tmp = new File(PropertyManager.getInstance().getProperties().getProperty("tmpDir", "tmp/"));
			FileUtils.deleteDirectory(tmp);
			tmp.mkdir();
			
			File sharedDir = new File(PropertyManager.getInstance().getProperties().getProperty("sharedDir", "shared/"));
			sharedDir.mkdir();
		} catch (Exception ex) {
			Cloud.getLogger().catching(ex);
		}
	}

}