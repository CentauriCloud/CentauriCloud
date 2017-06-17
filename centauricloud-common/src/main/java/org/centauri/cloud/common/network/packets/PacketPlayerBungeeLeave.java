package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPlayerBungeeLeave implements Packet {

	@Getter private UUID uuid;

	@Override
	public void encode(ByteBuf buf) {
		writeUUID(this.uuid, buf);
	}

	@Override
	public void decode(ByteBuf buf) {
		this.uuid = readUUID(buf);
	}

}
