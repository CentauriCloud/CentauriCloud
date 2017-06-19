package org.centauri.cloud.cloud.template;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;

public class TemplateManager {
	
	@Getter private Set<Template> templates = new HashSet<>();
	
	public TemplateManager() {
		this.createDefaultDirectories();
		boolean importTemplates = Boolean.valueOf(PropertyManager.getInstance().getProperties().getProperty("autoloadTemplates", "true"));
		if(importTemplates)
			this.importAllTemplates();
	}
	
	public void removeTemplate(String name) throws Exception {
		Template template = this.getTemplate(name);
		if(template == null) {
			Cloud.getLogger().warn("Cannot find template {}!", name);
			return;
		}

		template.getPropertiesInputStream().close();
		FileUtils.deleteDirectory(template.getDir());
		this.templates.remove(template);
        Cloud.getLogger().info("Removed template {}!", name);
	}
	
	public void loadTemplate(String name) throws Exception {
		CentauriProfiler.Profile profile = Cloud.getInstance().getProfiler().start("TemplateManager_loadTemplate_" + name);
		String templatesDirPath = PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/") + name + "/";
		
		Template template = new Template(name, new File(templatesDirPath), new File(templatesDirPath, "centauricloud.properties"));
		template.getDir().mkdir();

		//creates a template if not exists
		if(!template.getConfig().exists()) {
			Files.copy(this.getClass().getResourceAsStream("/centauricloud.yml"), template.getConfig().toPath());
			Cloud.getLogger().info("Created Template {}!", name);
		}
		
		template.loadConfig();
		template.loadSharedFiles();

		this.templates.add(template);

		Cloud.getInstance().getProfiler().stop(profile);
	}

	public Template getTemplate(String name) {
		return templates.stream().filter(template -> template.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
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
	
	private void importAllTemplates() {
		Cloud.getLogger().info("Autoload all templates...");
		File templatesDir = new File(PropertyManager.getInstance().getProperties().getProperty("templatesDir", "templates/"));
		for(File templateDir : templatesDir.listFiles()) {
			if(templateDir.isDirectory()) {
				try{
					this.loadTemplate(templateDir.getName());
					this.getTemplate(templateDir.getName()).compress();
				} catch (Exception ex) {
					Cloud.getLogger().catching(ex);
				}
			}
		}
		Cloud.getLogger().info("Finished loading all templates!");
	}

}