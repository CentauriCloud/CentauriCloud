package org.centauri.cloud.cloud.module;

import lombok.Getter;
import lombok.SneakyThrows;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.Config;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ModuleLoader extends Config {

	@Getter private List<Module> loaded = new ArrayList<>();
	private ScheduledExecutorService scheduler;

	@SneakyThrows
	public void loadFiles(File dir, ClassLoader loader) {
		CentauriProfiler.Profile profile = Cloud.getInstance().getProfiler().start("ModuleLoader_loadFiles");
		dir.mkdirs();

		File[] fls = dir.listFiles((dir1, name) -> name.endsWith(".jar"));
		List<File> files = Arrays.asList(fls);

		URL[] urls = new URL[files.size()];
		for (int i = 0; i < files.size(); i++)
			urls[i] = files.get(i).toURI().toURL();
		URLClassLoader ucl = new URLClassLoader(urls, loader);

		ServiceLoader<Module> serviceLoader = ServiceLoader.load(Module.class, ucl);
		Iterator<Module> iterator = serviceLoader.iterator();
		try {
			while (iterator.hasNext()) {
				Module module = iterator.next();
				if (!loaded.contains(module)) {
					Module oldModule = loaded.stream().filter(module1 -> module1.getName().equals(module.getName())).findAny().orElse(null);
					if (oldModule != null) {
						oldModule.onDisable();
						loaded.remove(oldModule);
					}
					loaded.add(module);
					module.onEnable();
					Cloud.getLogger().info("{} from: {} version: {}", module.getName(), module.getAuthor(), module.getVersion());
				}
			}
		} catch (Exception e) {
			Cloud.getLogger().error("Error", e);
		}

		Cloud.getInstance().getProfiler().stop(profile);
	}

	public void initializeScheduler() {
		File file = new File(get("modulesDir"));

		if (!file.exists()) {
			file.mkdir();
		}

		Cloud.getLogger().info("Load modules file ({})...", file.getAbsolutePath());
		scheduler = Executors.newScheduledThreadPool(1);
		ClassLoader classLoader = Cloud.class.getClassLoader();
		scheduler.scheduleAtFixedRate(() -> loadFiles(file, classLoader), 0, 10, TimeUnit.SECONDS);
	}

	public void stop() {
		scheduler.shutdownNow();
	}
}
