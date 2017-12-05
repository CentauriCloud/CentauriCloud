package org.centauri.cloud.cloud.module.library;

import lombok.Getter;
import lombok.SneakyThrows;
import org.centauri.cloud.cloud.Cloud;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibraryLoader {

	@Getter private Set<String> loadedLibs = new HashSet<>();
	private final ClassLoader cloudClassloader = Cloud.class.getClassLoader();

	@SneakyThrows
	public void loadLibs(File dir) {
		dir.mkdir();

		List<File> libFiles = Arrays.asList(dir.listFiles((dir1, name) -> name.contains(".jar")));

		URL[] urls = new URL[libFiles.size()];
		for (int i = 0; i < libFiles.size(); i++)
			urls[i] = libFiles.get(i).toURI().toURL();

		for (URL url : urls) {
			this.loadLib(url);
		}
	}

	private void loadLib(URL url) throws Exception {
		this.loadLib(url, this.cloudClassloader);
	}

	public void loadLib(URL url, ClassLoader classloader) throws Exception {
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		method.setAccessible(true);
		method.invoke(classloader, url);
		this.loadedLibs.add(url.getFile());
	}

}