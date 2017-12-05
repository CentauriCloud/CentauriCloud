package org.centauri.cloud.cloud;

import org.centauri.cloud.common.network.PacketManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CloudTest {

	@Test
	public void testPacketsCreationFile() {
		Cloud cloud = new Cloud();
		cloud.createPacketsFile();
		File file = new File(cloud.getSharedDir(), "Packets.txt");
		try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
			List<String> lines = inputStream.lines().collect(Collectors.toList());
			for (int i = 0; i < lines.size(); i++) {
				assertEquals(PacketManager.getInstance().getPackets().get(i).getSimpleName(), lines.get(i));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void assertEquals(String expected, String actual) {
		System.out.println("Expected:\t" + expected);
		System.out.println("Actual:  \t" + actual);
		Assert.assertEquals(expected, actual);
	}

}