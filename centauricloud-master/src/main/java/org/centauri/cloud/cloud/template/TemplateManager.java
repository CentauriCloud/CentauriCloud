package org.centauri.cloud.cloud.template;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateManager {

	@Getter
	private Set<Template> templates = new HashSet<>();

	public TemplateManager() {
		this.createDefaultDirectories();
		boolean importTemplates = Boolean.valueOf(PropertyManager.getInstance().getProperties().getProperty("autoloadTemplates", "true"));
		if (importTemplates)
			this.importAllTemplates();
	}

	public void removeTemplate(String name) throws Exception {
		Template template = this.getTemplate(name);
		if (template == null) {
			Cloud.getLogger().warn("Cannot find template {}!", name);
			return;
		}

		FileUtils.deleteDirectory(template.getDir());
		this.templates.remove(template);
		Cloud.getLogger().info("Removed template {}!", name);
	}

	public void loadTemplate(String name) throws Exception {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches())
			throw new UnsupportedOperationException("Wrong name");

		CentauriProfiler.Profile profile = Cloud.getInstance().getProfiler().start("TemplateManager_loadTemplate_" + name);

		File templateDir = new File(Cloud.getInstance().getTemplatesDir(), name + "/");
		Template template = new Template(name, templateDir, new File(templateDir, "/centauricloud.yml"));
		template.getDir().mkdir();

		//creates a template if not exists
		if (!template.getConfig().exists()) {
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
			Cloud.getInstance().getTemplatesDir().mkdir();

			FileUtils.deleteDirectory(Cloud.getInstance().getTmpDir());
			Cloud.getInstance().getTmpDir().mkdir();

			Cloud.getInstance().getSharedDir().mkdir();
		} catch (Exception ex) {
			Cloud.getLogger().catching(ex);
		}
	}

	private void importAllTemplates() {
		Cloud.getLogger().info("Autoload all templates...");
		for (File templateDir : Cloud.getInstance().getTemplatesDir().listFiles()) {
			if (templateDir.isDirectory()) {
				try {
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