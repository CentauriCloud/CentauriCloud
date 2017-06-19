package org.centauri.cloud.cloud.module;

import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.Config;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	private List<String> md5HashesLoaded = new ArrayList<>();
	private ScheduledExecutorService scheduler;

	public void loadFiles(File dir, ClassLoader loader) throws IOException {
		CentauriProfiler.Profile profile = Cloud.getInstance().getProfiler().start("ModuleLoader_loadFiles");
		dir.mkdirs();

		File[] fls = dir.listFiles((dir1, name) -> name.endsWith(".jar"));
		List<File> files = Arrays.asList(fls);
		System.out.println(files);
		Iterator<File> fileIterator = files.iterator();
		while (fileIterator.hasNext()) {
			File file = fileIterator.next();
			System.out.println(file.getName());
			try (FileInputStream inputStream = new FileInputStream(file)) {
				String md5 = DigestUtils.md5Hex(inputStream);
				System.out.println(md5);
				if (md5HashesLoaded.contains(md5)) {
					files.remove(file);
				} else {
					md5HashesLoaded.add(md5);
				}
			}
		}

		URL[] urls = new URL[files.size()];
		for (int i = 0; i < files.size(); i++)
			urls[i] = files.get(i).toURI().toURL();
		URLClassLoader ucl = new URLClassLoader(urls, loader);

		ServiceLoader<Module> serviceLoader = ServiceLoader.load(Module.class, ucl);
		Iterator<Module> iterator = serviceLoader.iterator();


		try {
			while (iterator.hasNext()) {
				Module module = iterator.next();
				Module oldModule = loaded.stream().filter(module1 -> module1.getName().equals(module.getName())).findAny().orElse(null);
				if (oldModule != null) {
					try {
						oldModule.onDisable();
					} catch (Exception ex) {
						Cloud.getLogger().error("Error", ex);
					}
					loaded.remove(oldModule);
				}
				loaded.add(module);
				try {
					module.onEnable();
				} catch (Exception ex) {
					Cloud.getLogger().error("Error", ex);
				}
				Cloud.getLogger().info("{} from: {} version: {}", module.getName(), module.getAuthor(), module.getVersion());
			}
		} catch (Exception e) {
			Cloud.getLogger().error("Error", e);
		}

		Cloud.getInstance().getProfiler().stop(profile);
	}

	public void initializeScheduler() {
		File file = new File(getConfig("modulesDir"));

		if (!file.exists()) {
			file.mkdir();
		}

		Cloud.getLogger().info("Load modules file ({})...", file.getAbsolutePath());
		scheduler = Executors.newScheduledThreadPool(1);
		ClassLoader classLoader = Cloud.class.getClassLoader();
		scheduler.scheduleWithFixedDelay(() -> {
			try {
				loadFiles(file, classLoader);
			} catch (IOException e) {
				Cloud.getLogger().error("Ex", e);
			}
		}, 0, 30, TimeUnit.SECONDS);
	}

	public void stop() {
		scheduler.shutdownNow();
	}
}
