package org.centauri.cloud.cloud.download;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.centauri.cloud.cloud.Cloud;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.centauri.cloud.cloud.util.ProgressUtil;

public class ConnectorDownloader {

	public void checkConnectorsAndDownload() {
		for (ConnectorType type : ConnectorType.values()) {
			if (!type.getFile().exists()) {
				download(type);
			}
		}
	}

	private void download(ConnectorType type) {
		try {
			URL url = new URL(type.getRemotePath());
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("HEAD");
			urlConnection.getInputStream();
			int allBytes = urlConnection.getContentLength();
			urlConnection.disconnect();

			File targetFile = type.getFile();
			if(!targetFile.exists())
				targetFile.createNewFile();
			
			try (InputStream fileInputStream = url.openStream();
				OutputStream outputStream = new FileOutputStream(targetFile)) {

				int bufferSize = 4 * 1024;
				byte[] buffer = new byte[bufferSize];
				int count = 0;
				int read;
				
				System.out.println("Progress for downloading " + type.getFile().getName() + ":");
				System.out.println("");

				while ((read = fileInputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, read);
					count += read;
					ProgressUtil.printProgress(allBytes, count);
				}

				System.out.println("");
				System.out.println("");

			}
			
		} catch (MalformedURLException e) {
			Cloud.getLogger().error("Cannot create url", e);
		} catch (IOException e) {
			Cloud.getLogger().error("Cannot open stream, webserver down? Please contact CentauriTeam!", e);
		}
	}

	public enum ConnectorType {
		SPIGOT(new File("shared/CentauriCloudSpigot.jar"), "https://centauricloud.net/downloads/latest/CentauriCloudSpigot.jar"), BUNGEE(new File("shared/CentauriCloudBungee.jar"), "https://centauricloud.net/downloads/latest/CentauriCloudBungee.jar");

		@Getter private final File file;
		@Getter private final String remotePath;

		ConnectorType(File file, String remotePath) {
			this.file = file;
			this.remotePath = remotePath;
		}
	}
}
