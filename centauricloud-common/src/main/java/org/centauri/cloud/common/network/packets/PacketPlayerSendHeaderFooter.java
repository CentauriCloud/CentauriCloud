
package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPlayerSendHeaderFooter implements Packet {

	@Getter private UUID uuid;
	@Getter private String header;
	@Getter private String footer;

	@Override
	public void encode(ByteBuf buf) {
		writeUUID(this.uuid, buf);
		writeString(this.header, buf);
		writeString(this.footer, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.uuid = readUUID(buf);
		this.header = readString(buf);
		this.footer = readString(buf);
	}

}
