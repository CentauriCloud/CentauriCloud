package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PacketRequestConsole implements Packet {

	@Getter private int from;
	@Getter private int to;

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(from);
		buf.writeInt(to);
	}

	@Override
	public void decode(ByteBuf buf) {
		from = buf.readInt();
		to = buf.readInt();
	}
}
