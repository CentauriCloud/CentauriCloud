package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PacketToServerDispatchCommand implements Packet {

	@Getter private String command;

	@Override
	public void encode(ByteBuf buf) {
		writeString(command, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		command = readString(buf);
	}
}
