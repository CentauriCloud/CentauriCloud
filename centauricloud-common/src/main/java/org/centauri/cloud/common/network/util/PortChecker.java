package org.centauri.cloud.common.network.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class PortChecker {

	public static boolean available(int port) {
		DatagramSocket ds = null;

		try (ServerSocket ss = new ServerSocket(port)) {
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

		return false;
	}

}