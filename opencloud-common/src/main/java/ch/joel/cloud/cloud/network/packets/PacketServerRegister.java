package ch.joel.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.nio.charset.Charset;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PacketServerRegister implements SimplePacket {

	private String serverName;

	@Override
	public void read(ByteBuf byteBuf) throws IOException {
		int length = byteBuf.readInt();
		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);
		serverName = new String(bytes, Charset.forName("UTF-8"));
	}

	@Override
	public void write(ByteBuf byteBuf) throws IOException {
		byteBuf.writeInt(serverName.getBytes().length);
		byteBuf.writeBytes(serverName.getBytes(Charset.forName("UTF-8")));
	}
}
