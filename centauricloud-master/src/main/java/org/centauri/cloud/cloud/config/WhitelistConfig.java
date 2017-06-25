package org.centauri.cloud.cloud.config;

import org.centauri.cloud.cloud.Cloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class WhitelistConfig {

	private final File configFile;

	public WhitelistConfig() throws IOException {
		this.configFile = new File("whitelist.config");

		if (!this.configFile.exists()) {
			this.configFile.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(this.configFile);
			try {
				outputStream.write("127.0.0.1".getBytes());
			} finally {
				outputStream.close();
			}
		}


		BufferedReader reader = new BufferedReader(new FileReader(this.configFile));
		try {
			String hostString;
			while ((hostString = reader.readLine()) != null) {
				Cloud.getInstance().getWhitelistedHosts().add(hostString);
			}
		} finally {
			reader.close();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Whitelisted hosts: ");
		for (String hostString : Cloud.getInstance().getWhitelistedHosts()) {
			sb.append(hostString);
			sb.append(", ");
		}

		Cloud.getLogger().info(sb.toString());
	}

}