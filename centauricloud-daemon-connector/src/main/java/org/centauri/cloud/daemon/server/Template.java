package org.centauri.cloud.daemon.server;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class Template {

	@Getter private String name;
	@Getter private File originalDirectory;

	public Template(String name) {
		this.name = name;
		this.originalDirectory = new File("templates/" + name);
	}

	@SneakyThrows
	public File createCopy() {
		File dir = this.findCopyDir();
		if (dir == null)
			return null;
		FileUtils.copyDirectory(this.originalDirectory, dir);
		return dir;
	}

	private File findCopyDir() {
		File copyDir = null;

		for (int i = 0; i < 100; i++) {
			copyDir = new File("tmp/" + name + "-" + i);
			if (!copyDir.exists())
				return copyDir;
		}

		return null;
	}

}