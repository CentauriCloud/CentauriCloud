package org.centauri.cloud.cloud.download;

import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.util.ProgressUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectorDownloader {

	public void checkConnectorsAndDownload() {
		for (ConnectorType type : ConnectorType.values()) {
			if (!new File(Cloud.getInstance().getSharedDir(), type.getFile().getName()).exists()) {
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

			File targetFile = new File(Cloud.getInstance().getSharedDir(), type.getFile().getCanonicalFile().getName());
			if (!targetFile.exists())
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
			Cloud.getLogger().error("Cannot open stream, webserver down? Please contact CentauriTeam! Code: NO_CON");
		}
	}

	public enum ConnectorType {
		SPIGOT(new File("CentauriCloudSpigot.jar"), "https://centauricloud.net/downloads/latest/CentauriCloudSpigot.jar"), BUNGEE(new File("CentauriCloudBungee.jar"), "https://centauricloud.net/downloads/latest/CentauriCloudBungee.jar");

		@Getter private final File file;
		@Getter private final String remotePath;

		ConnectorType(File file, String remotePath) {
			this.file = file;
			this.remotePath = remotePath;
		}
	}
}
