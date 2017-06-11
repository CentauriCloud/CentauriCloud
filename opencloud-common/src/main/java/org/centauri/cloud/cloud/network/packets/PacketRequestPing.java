package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PacketRequestPing implements Packet{
	
	@Override
	public void encode(ByteBuf buf) {
	}

	@Override
	public void decode(ByteBuf buf) {
	}
	
}
