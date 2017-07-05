package org.centauri.cloud.common.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.centauri.cloud.common.network.server.ServerType;

@NoArgsConstructor
@AllArgsConstructor
public class PacketServerRegister implements Packet {

	@Getter private String prefix;
	@Getter private ServerType type;
	@Getter private int bukkitPort;

	@Override
	public void encode(ByteBuf buf) {
		this.writeString(prefix, buf);
		buf.writeByte(this.type.ordinal());
		if (type == ServerType.SPIGOT)
			buf.writeInt(this.bukkitPort);
	}

	@Override
	public void decode(ByteBuf buf) {
		prefix = this.readString(buf);
		type = ServerType.values()[buf.readByte()];
		if (this.type == ServerType.SPIGOT)
			this.bukkitPort = buf.readInt();
	}
}
