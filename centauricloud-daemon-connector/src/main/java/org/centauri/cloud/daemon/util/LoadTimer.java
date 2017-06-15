package org.centauri.cloud.daemon.util;

import org.centauri.cloud.common.network.packets.PacketServerLoad;
import org.centauri.cloud.daemon.Daemon;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Timer;
import java.util.TimerTask;

public class LoadTimer {
	
	public LoadTimer() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				LoadTimer.this.sendLoad();
			}
		}, 30 * 1000, 30 * 1000);
	}
	
	private void sendLoad() {
		long freeRam = 0;
		double cpuLoad = 0;
		
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get")
					&& Modifier.isPublic(method.getModifiers())) {
				Object value = 0;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				if(method.getName().equals("getFreePhysicalMemorySize"))
					freeRam = (long) value;
				if(method.getName().equals("getSystemCpuLoad"))
					cpuLoad = (double) value;				
			}
		}
		
		Daemon.getInstance().getClient().getChannel().writeAndFlush(new PacketServerLoad(freeRam, cpuLoad));
	}
}