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
public class PacketToServerDispatchCommand implements Packet {

	private String command;

	@Override
	public void encode(ByteBuf buf) {
		writeString(command, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		command = readString(buf);
	}
}
