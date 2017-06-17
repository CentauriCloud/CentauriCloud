package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPlayerServerJoin implements Packet {

	@Getter private UUID uuid;
	@Getter private String ip;
	@Getter private String name;
	@Getter private boolean bungeeJoin;

	@Override
	public void encode(ByteBuf buf) {
		writeUUID(this.uuid, buf);
		if (this.bungeeJoin) {
			writeString(this.ip, buf);
			writeString(this.name, buf);
			buf.writeBoolean(this.bungeeJoin);
		}
	}

	@Override
	public void decode(ByteBuf buf) {
		if (buf.readableBytes() != 16) {
			this.uuid = readUUID(buf);
			this.ip = readString(buf);
			this.name = readString(buf);
			this.bungeeJoin = buf.readBoolean();
		} else {
			this.uuid = readUUID(buf);
			this.ip = null;
			this.name = null;
			this.bungeeJoin = false;
		}
	}

}
