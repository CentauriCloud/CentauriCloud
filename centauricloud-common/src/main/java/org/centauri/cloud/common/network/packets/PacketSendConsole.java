package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PacketSendConsole implements Packet {

	@Getter private List<String> lines = new ArrayList<>();

	@Override
	public void encode(ByteBuf buf) {
		buf.writeByte(lines.size());
		for (String line : lines) {
			writeString(line, buf);
		}
	}

	@Override
	public void decode(ByteBuf buf) {
		int amount = buf.readByte();
		for (int i = 0; i < amount; i++) {
			lines.add(readString(buf));
		}
	}
}
