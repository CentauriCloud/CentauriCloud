package org.centauri.cloud.cloud.download;

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

public final class DownloadUtil {

	private DownloadUtil() {
	}

	public static void download(String urlString, File target) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("HEAD");
			urlConnection.getInputStream();
			int allBytes = urlConnection.getContentLength();
			urlConnection.disconnect();

			if (!target.exists())
				target.createNewFile();

			try (InputStream fileInputStream = url.openStream();
				 OutputStream outputStream = new FileOutputStream(target)) {

				int bufferSize = 4 * 1024;
				byte[] buffer = new byte[bufferSize];
				int count = 0;
				int read;

				System.out.println("Progress for downloading " + target.getName() + ":");
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
			Cloud.getLogger().error("Cannot open stream, webserver down? Please contact CentauriTeam! Code: NO_CON", e);
		}
	}

}
