package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketTemplateData implements Packet {

	private byte[] templateData;
	
	@Override
	public void encode(ByteBuf buf) {
		writeBytes(templateData, buf);
;	}

	@Override
	public void decode(ByteBuf buf) {
		this.templateData = readBytes(buf);
	}

}