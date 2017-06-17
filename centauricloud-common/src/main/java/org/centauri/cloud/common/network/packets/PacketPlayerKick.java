
package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPlayerKick implements Packet {

	@Getter private UUID uuid;
	@Getter private String message;

	@Override
	public void encode(ByteBuf buf) {
		writeUUID(this.uuid, buf);
		writeString(this.message, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.uuid = readUUID(buf);
		this.message = readString(buf);
	}

}
