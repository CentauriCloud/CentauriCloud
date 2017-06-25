package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PacketPing implements Packet {

	@Getter private long timestamp;

	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(timestamp);
	}

	@Override
	public void decode(ByteBuf buf) {
		timestamp = buf.readLong();
	}
}
