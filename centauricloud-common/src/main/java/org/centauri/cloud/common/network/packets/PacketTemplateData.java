package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketTemplateData implements Packet {

	@Getter private String templateName;
	@Getter private byte[] templateData;
	
	@Override
	public void encode(ByteBuf buf) {
		writeString(templateName, buf);
		writeBytes(templateData, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.templateName = readString(buf);
		this.templateData = readBytes(buf);
	}

}