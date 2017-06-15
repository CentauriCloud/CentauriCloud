package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PacketServerDisconnect implements Packet {

	@Getter private String prefix;

	@Override
	public void encode(ByteBuf byteBuf) {
		writeString(prefix, byteBuf);
	}

	@Override
	public void decode(ByteBuf byteBuf) {
		prefix = readString(byteBuf);
	}
}
