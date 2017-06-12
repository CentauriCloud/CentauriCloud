package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacketServerDisconnect implements Packet {

	private String prefix;

	@Override
	public void encode(ByteBuf byteBuf) {
		writeString(prefix, byteBuf);
	}

	@Override
	public void decode(ByteBuf byteBuf) {
		prefix = readString(byteBuf);
	}
}
