package org.centauri.cloud.cloud.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.common.network.config.TemplateConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
public class Template {

	@Getter private final String name;
	@Getter private final File dir;
	@Getter private final File config;
	@Getter private int minServersFree;
	@Getter private int maxPlayers;
	@Getter private TemplateConfig templateConfig;
	@Getter private Map<File, File> dependencies = new HashMap<>();
	@Getter private TemplateType type;

	public void loadConfig() throws Exception {
		this.templateConfig = new TemplateConfig(this.dir);
		this.minServersFree = (int) this.templateConfig.getOrElse("template.minServersFree", 1);
		this.maxPlayers = (int) this.templateConfig.getOrElse("template.maxPlayers", 16);
		this.type = TemplateType.valueOf((String) this.templateConfig.getOrElse("type", "CUSTOM"));
	}

	public void loadSharedFiles() throws Exception {
		DependencieResolver.resolveDependencies(this);
	}

	public void build() throws Exception {
		this.getDependencies().forEach((src, dest) -> {
			try {
				if (!src.exists()) {
					Cloud.getLogger().warn("Cannot find shared file {}({}) for template {}", src.getName(), src.getAbsolutePath(), this.name);
					return;
				}

				if (dest.exists())
					FileUtils.deleteQuietly(dest);

				if (src.isDirectory()) {
					copyFolder(src, dest);
				} else {
					Files.copy(src.toPath(), dest.toPath());
				}

			} catch (Exception ex) {
				Cloud.getLogger().catching(ex);
			}
		});

		String destinationFileName = this.type == TemplateType.SPIGOT ? "CentauriCloudSpigot.jar" : "CentauriCloudBungee.jar";
		File connector = this.type == TemplateType.CUSTOM ? null : new File(Cloud.getInstance().getSharedDir(), destinationFileName);
		File pluginsDir = new File(this.dir, "plugins/");
		if (!pluginsDir.exists())
			pluginsDir.mkdir();

		if (connector == null) {
			Cloud.getLogger().info("Cannot load connector for, because this is a custom template or the connector cannot be found...");
		} else {
			File dest = new File(pluginsDir, destinationFileName);
			if (dest.exists())
				dest.delete();
			Files.copy(connector.toPath(), dest.toPath());
		}

		if(this.type == TemplateType.SPIGOT) {
			File serverProperties = new File(this.dir, "server.properties");
			if(!serverProperties.exists())
				Files.copy(this.getClass().getClassLoader().getResourceAsStream("spigot_server.properties"), serverProperties.toPath());
		} else if(this.type == TemplateType.BUNGEE) {
			File bungeeConfig = new File(this.dir, "config.yml");
			if(!bungeeConfig.exists())
				Files.copy(this.getClass().getClassLoader().getResourceAsStream("bungee_config.yml"), bungeeConfig.toPath());
		}
		
	}

	@SneakyThrows
	public void compress() {
		File file = new File("shared/Packets.txt");
		FileUtils.copyFile(file, new File(getDir().getAbsolutePath() + "/Packets.txt"));
		compressZipfile(this.getDir().getPath() + "/", Cloud.getInstance().getTmpDir().getPath() + "/" + this.name + ".zip");
		Cloud.getLogger().info("Compressed template {} into a zip file!", this.name);
	}

	private void compressZipfile(String sourceDir, String outputFile) throws Exception {
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputFile))) {
			compressDirectoryToZipfile(sourceDir, sourceDir, zos);
		}
	}

	private void compressDirectoryToZipfile(String rootDir, String sourceDir, ZipOutputStream out) throws Exception {
		for (File file : new File(sourceDir).listFiles()) {
			if (file.isDirectory()) {
				compressDirectoryToZipfile(rootDir, sourceDir + file.getName() + "/", out);
			} else {
				ZipEntry entry = new ZipEntry(sourceDir.replace(rootDir, "") + file.getName());
				out.putNextEntry(entry);

				try (FileInputStream in = new FileInputStream(sourceDir + file.getName())) {
					IOUtils.copy(in, out);
				}
			}
		}
	}

	@SneakyThrows
	static private void copyFolder(File src, File dest) {
		if (src == null || dest == null)
			return;

		if (!src.isDirectory()) {
			FileUtils.copyFile(src, dest);
			return;
		}

		if (dest.exists()) {
			if (!dest.isDirectory()) {
				return;
			}
		} else {
			dest.mkdir();
		}

		if (src.listFiles() == null || src.listFiles().length == 0)
			return;

		for (File file : src.listFiles()) {
			File fileDest = new File(dest, file.getName());
			if (file.isDirectory()) {
				copyFolder(file, fileDest);
			} else {
				if (fileDest.exists())
					continue;
				Files.copy(file.toPath(), fileDest.toPath());
			}
		}
	}

	public static enum TemplateType {
		SPIGOT, BUNGEE, CUSTOM
	}

}