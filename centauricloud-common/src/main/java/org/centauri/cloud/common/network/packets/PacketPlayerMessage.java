
package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPlayerMessage implements Packet {

	@Getter private UUID uuid;
	@Getter private String message;
	@Getter private boolean raw;

	@Override
	public void encode(ByteBuf buf) {
		writeUUID(this.uuid, buf);
		writeString(this.message, buf);
		buf.writeBoolean(this.raw);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.uuid = readUUID(buf);
		this.message = readString(buf);
		this.raw = buf.readBoolean();
	}

}
