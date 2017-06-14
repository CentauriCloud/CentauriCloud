package org.centauri.cloud.cloud.template;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;

@RequiredArgsConstructor
public class Template {
	
	@Getter private final String name;
	@Getter private final File dir;
	@Getter private final File config;
	@Getter private Properties properties;
	@Getter private FileInputStream propertiesInputStream;
	@Getter private Set<SharedFile> sharedFiles = new HashSet<>();
	
	public void loadSharedFiles() throws Exception {
		this.properties = new Properties();
		this.propertiesInputStream = new FileInputStream(this.config);
		this.properties.load(this.getPropertiesInputStream());
		
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
			Files.copy(sharedFile.getFile().toPath(), new File(this.dir.getPath() + sharedFile.getName()).toPath());
			Cloud.getLogger().info("Copyed shared file {} of template {}", sharedFile.getName(), this.name);
		}
	}
	
	public void compress() {
		
	}

}