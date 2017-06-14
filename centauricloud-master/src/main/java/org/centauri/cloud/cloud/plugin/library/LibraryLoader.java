package org.centauri.cloud.cloud.plugin.library;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.SneakyThrows;

public class LibraryLoader {

	@Getter private Set<String> loadedLibs = new HashSet<>();
	
	@SneakyThrows
	public void loadLibs(File dir, ClassLoader loader) {
		dir.mkdir();
		
		List<File> libFiles = Arrays.asList(dir.listFiles((dir1, name) -> name.contains(".jar")));
		
		URL[] urls = new URL[libFiles.size()];
		for (int i = 0; i < libFiles.size(); i++)
			urls[i] = libFiles.get(i).toURI().toURL();
		
		for(URL url : urls) {
			this.loadLib(loader, url);
		}
	}
	
	private void loadLib(ClassLoader loader, URL url) throws Exception {
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		method.setAccessible(true);
		method.invoke(loader, url);
		this.loadedLibs.add(url.getFile());
	}
	
}