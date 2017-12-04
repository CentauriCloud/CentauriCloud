package org.centauri.cloud.cloud.download;

import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;

import java.io.File;

public class ConnectorDownloader {

	public void checkConnectorsAndDownload() {
		for (ConnectorType type : ConnectorType.values()) {
			if (!new File(Cloud.getInstance().getSharedDir(), type.getFile().getName()).exists()) {
				DownloadUtil.download(type.getRemotePath(), type.getFile());
			}
		}
	}

	public enum ConnectorType {
		SPIGOT(new File(Cloud.getInstance().getSharedDir(), "CentauriCloudSpigot.jar"), "https://centauricloud.net/downloads/latest/CentauriCloudSpigot.jar"), BUNGEE(new File(Cloud.getInstance().getSharedDir(), "CentauriCloudBungee.jar"), "https://centauricloud.net/downloads/latest/CentauriCloudBungee.jar");

		@Getter private final File file;
		@Getter private final String remotePath;

		ConnectorType(File file, String remotePath) {
			this.file = file;
			this.remotePath = remotePath;
		}
	}
}
