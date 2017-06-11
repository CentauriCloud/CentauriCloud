package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.Charset;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PacketServerRegister implements Packet {

	private String prefix;
	
	@Override
	public void encode(ByteBuf byteBuf) {
		byteBuf.writeInt(prefix.getBytes().length);
		byteBuf.writeBytes(prefix.getBytes(Charset.forName("UTF-8")));
	}
	
	@Override
	public void decode(ByteBuf byteBuf) {
		int length = byteBuf.readInt();
		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);
		prefix = new String(bytes, Charset.forName("UTF-8"));
	}

	@Override
	public byte getId() {
		return 0x01;
	}

}
