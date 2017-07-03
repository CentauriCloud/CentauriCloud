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
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ModuleLoader extends Config {

	@Getter private List<Module> loaded = new ArrayList<>();
	private List<String> md5HashesLoaded = new ArrayList<>();

	public void loadFiles(File dir, ClassLoader loader) throws IOException {
		CentauriProfiler.Profile profile = Cloud.getInstance().getProfiler().start("ModuleLoader_loadFiles");
		dir.mkdirs();

		List<File> newFiles = Arrays.stream(dir.listFiles((dir1, name) -> name.endsWith(".jar"))).filter(file -> {
			try (FileInputStream fis = new FileInputStream(file)) {
				String md5Hash = DigestUtils.md5Hex(fis);
				boolean contains = md5HashesLoaded.contains(md5Hash);
				if (!contains)
					md5HashesLoaded.add(md5Hash);
				return !contains;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}).collect(Collectors.toList());

		URL[] urls = new URL[newFiles.size()];
		for (int i = 0; i < newFiles.size(); i++)
			urls[i] = newFiles.get(i).toURI().toURL();
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
		ClassLoader classLoader = Cloud.class.getClassLoader();

		try {
			loadFiles(file, classLoader);
		} catch (IOException ex) {
			Cloud.getLogger().catching(ex);
		}

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					loadFiles(file, classLoader);
				} catch (IOException e) {
					Cloud.getLogger().error("Ex", e);
				}
			}
		}, 30_000, 30_000);

	}
}
