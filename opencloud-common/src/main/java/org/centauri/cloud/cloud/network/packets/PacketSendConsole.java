package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacketSendConsole implements Packet {

	private List<String> lines = new ArrayList<>();

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
