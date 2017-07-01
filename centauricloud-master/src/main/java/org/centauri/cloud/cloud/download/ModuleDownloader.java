package org.centauri.cloud.cloud.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.Config;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.util.ProgressUtil;

public class ModuleDownloader extends Config {

	public void downloadLib(String remotePath, String destFileName) {
		this.download(remotePath, destFileName, false);
	}

	public void download(ModuleType type) {
		//download dependencies
		for(ModuleType dependency : type.getDependencies()) {
			this.download(dependency);
		}
		
		//download module
		this.download(type.createDownloadUrl(), type.getFinalName(), true);
	}
	
	public void download(String remotePath, String destFileName, boolean module) {
		try {
			URL url = new URL(remotePath);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("HEAD");
			urlConnection.getInputStream();
			int allBytes = urlConnection.getContentLength();
			urlConnection.disconnect();

			String modulesDir = "modules/";//TODO improve for installer

			if(PropertyManager.getInstance() != null)
				modulesDir = getConfig("modulesDir");

			File downloadDir = new File(modulesDir);
			downloadDir.mkdir();

			if(!module)
				downloadDir = Cloud.getInstance().getLibDir();

			File targetFile = new File(downloadDir, destFileName);

			if(!targetFile.exists())
				targetFile.createNewFile();
			
			try (InputStream fileInputStream = url.openStream();
				OutputStream outputStream = new FileOutputStream(targetFile)) {

				int bufferSize = 4 * 1024;
				byte[] buffer = new byte[bufferSize];
				int count = 0;
				int read;
				
				Cloud.getLogger().info("Progress for downloading " + destFileName + ":");
				Cloud.getLogger().info("");

				while ((read = fileInputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, read);
					count += read;
					ProgressUtil.printProgress(allBytes, count);
				}

				Cloud.getLogger().info("");
				Cloud.getLogger().info("");

			}
			
		} catch (MalformedURLException e) {
			Cloud.getLogger().error("Cannot create url", e);
		} catch (IOException e) {
			Cloud.getLogger().error("Cannot open stream, webserver down? Please contact CentauriTeam!", e);
		}
	}
	
	public enum ModuleType {
		PLAYERS("CentauriCloudPlayersModule.jar",
				"CentauriCloudPlayersModule.jar",
				new ModuleType[0]);
		
		@Getter private String finalName;
		@Getter private String downloadUrl;
		@Getter private ModuleType[] dependencies;

		private ModuleType(String finalName, String downloadUrl, ModuleType[] dependencies) {
			this.downloadUrl = downloadUrl;
			this.dependencies = dependencies;
			this.finalName = finalName;
		}
		
		public String createDownloadUrl() {
			if(!this.downloadUrl.contains("http")) {
				//use official repo
				this.downloadUrl = "https://centauricloud.net/downloads/latest/" + this.downloadUrl;
			}

			return this.downloadUrl;
		}

	}
}