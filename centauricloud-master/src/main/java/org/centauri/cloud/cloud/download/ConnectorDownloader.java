package org.centauri.cloud.cloud.download;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.centauri.cloud.cloud.Cloud;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectorDownloader {

	private static final String PROGRESS_TEMPLATE = "|                                                  | 0%";
	private static final int SPACERS = 50;


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

			InputStream fileInputStream = url.openStream();
			File targetFile = type.getFile();
			if(!targetFile.exists())
				targetFile.createNewFile();
			
			OutputStream outputStream = new FileOutputStream(targetFile);

			int bufferSize = 4 * 1024;
			byte[] buffer = new byte[bufferSize];
			int count = 0;
			int read;
			System.out.println("Progress for downloading " + type.getFile().getName() + ":");
			System.out.println("");

			while ((read = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
				count += read;
				printProgress(allBytes, count);
			}

			System.out.println("");
			System.out.println("");

			IOUtils.closeQuietly(fileInputStream);
			IOUtils.closeQuietly(outputStream);
		} catch (MalformedURLException e) {
			Cloud.getLogger().error("Cannot create url", e);
		} catch (IOException e) {
			Cloud.getLogger().error("Cannot open stream, webserver down? Please contact CentauriTeam!", e);
		}
	}

	private void printProgress(int max, int now) {
		String pattern = PROGRESS_TEMPLATE;
		int spacerReplaces = now * SPACERS / max;

		for (int i = 0; i < spacerReplaces; i++) {
			pattern = pattern.replaceFirst(" ", "=");
		}

		pattern = pattern.replace("0", Integer.toString(now * 100 / max));
		System.out.print("\r" + pattern);
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
