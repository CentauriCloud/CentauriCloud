package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacketPing implements Packet {
	
	private long timestamp;
	
	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(timestamp);
	}

	@Override
	public void decode(ByteBuf buf) {
		timestamp = buf.readLong();
	}
}
