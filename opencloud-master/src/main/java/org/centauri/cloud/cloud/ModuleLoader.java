package org.centauri.cloud.cloud;

import org.centauri.cloud.cloud.config.Config;
import org.centauri.cloud.cloud.plugin.Module;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ModuleLoader extends Config {

	private List<String> loaded = new ArrayList<>();


	@SneakyThrows
	public void loadFiles(File dir, ClassLoader loader) {
		dir.mkdirs();

		File[] fls = dir.listFiles((dir1, name) -> name.contains(".jar"));
		List<File> files = Arrays.asList(fls);
		files.removeIf(file -> loaded.contains(file.getName()));

		URL[] urls = new URL[files.size()];
		for (int i = 0; i < files.size(); i++)
			urls[i] = files.get(i).toURI().toURL();
		URLClassLoader ucl = new URLClassLoader(urls, loader);

		ServiceLoader<Module> serviceLoader = ServiceLoader.load(Module.class, ucl);
		Iterator<Module> iterator = serviceLoader.iterator();
		loaded.addAll(files.stream().map(File::getName).collect(Collectors.toList()));
		while (iterator.hasNext()) {
			Module module = iterator.next();
			module.onEnable();
			System.out.println(module.getName() + " from: " + module.getAuthor() + " version: " + module.getVersion());

		}
	}

	public void initializeScheduler() {
		File file = new File(get("modulesDir"));
		System.out.println(file.getPath());
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		ClassLoader classLoader = Cloud.class.getClassLoader();
		scheduler.scheduleAtFixedRate(() -> loadFiles(file, classLoader), 0, 10, TimeUnit.SECONDS);
	}


}
