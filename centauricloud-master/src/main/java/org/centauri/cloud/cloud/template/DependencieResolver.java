package org.centauri.cloud.cloud.template;

import lombok.SneakyThrows;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.common.network.config.TemplateConfig;

import java.io.File;
import java.util.List;

final class DependencieResolver {

	private DependencieResolver() {
	}

	@SneakyThrows
	public static void resolveDependencies(Template template) {
		TemplateConfig templateConfig = template.getTemplateConfig();
		List<?> dependencyStrings = templateConfig.getList("dependencies");
		dependencyStrings.forEach(obj -> {
			String dependencyString = (String) obj;
			String[] data = dependencyString.split(":");
			if (data.length != 2) {
				Cloud.getLogger().info("Cannot parse dependency of {}", template.getName());
				return;
			}

			File source = new File(Cloud.getInstance().getSharedDir().getPath() + "/" + data[0]);
			File dest = new File(template.getDir() + "/" + data[1]);
			template.getDependencies().put(source, dest);
		});

		Cloud.getLogger().info("Resolved dependencies of {}", template.getName());
	}

}