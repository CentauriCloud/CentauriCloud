package org.centauri.cloud.daemon;

import java.io.File;
import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.Properties;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.centauri.cloud.centauricloud.connector.netty.Client;
import org.centauri.cloud.daemon.config.CloudConfiguration;
import org.centauri.cloud.daemon.netty.NetworkHandler;
import org.centauri.cloud.daemon.util.LoadTimer;

@Log4j2
public class Daemon {
	
	@Getter private static Daemon instance;
	@Getter private Client client;
	@Getter private CloudConfiguration cloudConfiguration;
	
	public Daemon() {
		instance = this;
	}
	
	@SneakyThrows
	public void start(){
		Properties properties = new Properties();
		File configFile = new File("config.properties");
		if(!configFile.exists())
			Files.copy(this.getClass().getResourceAsStream("/config.properties"), configFile.toPath());
		properties.load(new FileInputStream(configFile));
		this.cloudConfiguration = new CloudConfiguration(properties);
	
		new Thread(() -> {
			System.out.println("Try to start netty client...");

			this.client = new Client(new NetworkHandler(), cloudConfiguration.getHostname(), cloudConfiguration.getPort());
			try {
				this.client.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}, "Netty-Thread").start();
		
		new LoadTimer();
	}
	
	public static void main(String... args) {
		new Daemon().start();
	}
	
	public void sendLoad() {
		long freeRam = 0;
		double cpuLoad = 0;
		
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get")
					&& Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
				} // try
				if(method.getName().equals("getFreePhysicalMemorySize"))
					freeRam = (long) value;
				if(method.getName().equals("getSystemCpuLoad"))
					cpuLoad = (double) value;
				
				//System.out.println(method.getName() + " = " + value);
			}
		}
		
		System.out.println("Cpu load: " + cpuLoad);
		System.out.println("Free ram: " + FileUtils.byteCountToDisplaySize(freeRam));
	}

}