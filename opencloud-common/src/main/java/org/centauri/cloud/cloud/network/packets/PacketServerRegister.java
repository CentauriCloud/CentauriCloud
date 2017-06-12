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
	
	@Override
	public void encode(ByteBuf byteBuf) {
		byteBuf.writeInt(prefix.getBytes().length);
		byteBuf.writeBytes(prefix.getBytes(Charset.forName("UTF-8")));
		byteBuf.writeByte(type.ordinal());
	}
	
	@Override
	public void decode(ByteBuf byteBuf) {
		int length = byteBuf.readInt();
		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);
		prefix = new String(bytes, Charset.forName("UTF-8"));
		type = ServerType.values()[byteBuf.readByte()];
	}
}
