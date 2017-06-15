package org.centauri.cloud.cloud.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;

@RequiredArgsConstructor
public class Template {
	
	@Getter private final String name;
	@Getter private final File dir;
	@Getter private final File config;
	@Getter private int minServersFree;
	@Getter private int maxPlayers;
	@Getter private Properties properties;
	@Getter private FileInputStream propertiesInputStream;
	@Getter private Set<SharedFile> sharedFiles = new HashSet<>();
	
	public void loadConfig() throws Exception {
		this.properties = new Properties();
		this.propertiesInputStream = new FileInputStream(this.config);
		this.properties.load(this.getPropertiesInputStream());
		this.minServersFree = Integer.valueOf(this.properties.getProperty("minServersFree", "0"));
		this.minServersFree = Integer.valueOf(this.properties.getProperty("maxPlayers", "16"));
	}
	
	public void loadSharedFiles() throws Exception {
		List<String> sharedFileNames = Arrays.asList(this.properties.getProperty("sharedFiles").split(","));
		File sharedDir = new File(PropertyManager.getInstance().getProperties().getProperty("sharedDir", "shared/"));
		for(String sharedFileName : sharedFileNames) {
			this.sharedFiles.add(new SharedFile(new File(sharedDir.getPath() + "/" + sharedFileName), sharedFileName));
		}
	}
	
	public void build() throws Exception {
		for(SharedFile sharedFile : this.sharedFiles) {
			if(!sharedFile.getFile().exists()) {
				Cloud.getLogger().warn("Cannot find shared file {}({}) for template {}", sharedFile.getName(), sharedFile.getFile().getPath(), this.name);
				continue;
			}
			
			File file = new File(this.dir.getPath() + "/" + sharedFile.getName());
			
			if(file.exists())
				FileUtils.deleteQuietly(file);
			Files.copy(sharedFile.getFile().toPath(), file.toPath());
			
			Cloud.getLogger().info("Copyed shared file {} of template {}", sharedFile.getName(), this.name);
		}
	}
	
	@SneakyThrows
	public void compress() {
		compressZipfile(this.getDir().getPath() + "/", PropertyManager.getInstance().getProperties().getProperty("tmpDir", "tmp/") + this.name+".zip");
		Cloud.getLogger().info("Compressed {} into a zip file!", this.name);
	}
	
	private void compressZipfile(String sourceDir, String outputFile) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputFile));
		compressDirectoryToZipfile(sourceDir, sourceDir, zos);
		IOUtils.closeQuietly(zos);
	}

	private void compressDirectoryToZipfile(String rootDir, String sourceDir, ZipOutputStream out) throws Exception {
		for (File file : new File(sourceDir).listFiles()) {
			if (file.isDirectory()) {
				compressDirectoryToZipfile(rootDir, sourceDir + file.getName() + "/", out);
			} else {
				ZipEntry entry = new ZipEntry(sourceDir.replace(rootDir, "") + file.getName());
				out.putNextEntry(entry);

				FileInputStream in = new FileInputStream(sourceDir + file.getName());
				IOUtils.copy(in, out);
				IOUtils.closeQuietly(in);
			}
		}
	}
}