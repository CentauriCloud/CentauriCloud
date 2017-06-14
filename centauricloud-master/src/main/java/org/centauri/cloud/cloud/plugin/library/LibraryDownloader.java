package org.centauri.cloud.cloud.plugin.library;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import lombok.SneakyThrows;
import org.centauri.cloud.cloud.Cloud;

public class LibraryDownloader {

	@SneakyThrows
	public void downloadLib(String url, String libDir, String outputName) {
		Cloud.getLogger().info("Download {}...", outputName);
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(libDir + outputName);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		Cloud.getLogger().info("Finished downloading {}!", outputName);
	}
	
}