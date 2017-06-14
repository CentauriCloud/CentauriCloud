package org.centauri.cloud.cloud.network.util;

import io.netty.channel.Channel;
import java.util.Timer;
import java.util.TimerTask;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.network.packets.PacketPing;

@RequiredArgsConstructor
public class Pinger {
	
	private final Channel channel;
	private final int interval;
	
	public void start() {
		new Timer("Netty-Pinger").scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(channel.isOpen())
					channel.writeAndFlush(new PacketPing(System.currentTimeMillis()));
			}
		}, 1000L, this.interval * 1000L);
	}
}
