package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.Charset;
import org.centauri.cloud.cloud.network.server.ServerType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PacketServerRegister implements Packet {

	private String prefix;
	private ServerType type;
	private int bukkitPort;
	
	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(this.prefix.getBytes().length);
		buf.writeBytes(this.prefix.getBytes(Charset.forName("UTF-8")));
		buf.writeByte(this.type.ordinal());
		if(type == ServerType.SPIGOT)
			buf.writeInt(this.bukkitPort);
	}
	
	@Override
	public void decode(ByteBuf buf) {
		int length = buf.readInt();
		byte[] bytes = new byte[length];
		buf.readBytes(bytes);
		prefix = new String(bytes, Charset.forName("UTF-8"));
		type = ServerType.values()[buf.readByte()];
		if(this.type == ServerType.SPIGOT)
			this.bukkitPort = buf.readInt();
	}
}
