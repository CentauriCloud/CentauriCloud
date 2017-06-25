package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketServerLoad implements Packet {

	@Getter private long freeRam;
	@Getter private double cpuLoad;

	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(this.freeRam);
		buf.writeDouble(this.cpuLoad);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.freeRam = buf.readLong();
		this.cpuLoad = buf.readDouble();
	}

}