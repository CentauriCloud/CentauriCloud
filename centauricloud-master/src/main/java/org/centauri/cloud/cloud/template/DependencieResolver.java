package org.centauri.cloud.cloud.template;

import java.io.File;
import java.util.List;
import lombok.SneakyThrows;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.common.network.config.TemplateConfig;
public class DependencieResolver {

	@SneakyThrows
	public static void resolveDependencies(Template template) {
		TemplateConfig templateConfig = template.getTemplateConfig();
		List<?> dependencyStrings = templateConfig.getList("dependencies");
		dependencyStrings.forEach(obj -> {
			String dependencyString = (String) obj;
			String[] data = dependencyString.split(":");
			if(data.length != 2) {
				Cloud.getLogger().info("Cannot parse dependency of {}", template.getName());
				return;
			}
			
			File sharedDir = new File(PropertyManager.getInstance().getProperties().getProperty("sharedDir", "shared/"));
			File source = new File(sharedDir.getPath() + "/" + data[0]);
			File dest = new File(template.getDir() + "/" + data[1]);
			template.getDependencies().put(source, dest);
		});
		
		Cloud.getLogger().info("Resolved dependencies of {}", template.getName());
	}

}