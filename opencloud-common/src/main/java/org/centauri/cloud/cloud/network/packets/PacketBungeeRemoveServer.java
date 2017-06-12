package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PacketBungeeRemoveServer implements Packet {
	
	@Getter private String name;

	@Override
	public void encode(ByteBuf buf) {
		writeString(name, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.name = readString(buf);
	}
	
}
