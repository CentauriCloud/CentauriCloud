package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketStartServer implements Packet {

	private String templateName;
	
	@Override
	public void encode(ByteBuf buf) {
		writeString(templateName, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.templateName = readString(buf);
	}

}