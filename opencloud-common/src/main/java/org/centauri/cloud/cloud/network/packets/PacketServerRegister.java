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
	private ServerType type;
	
	@Override
	public void encode(ByteBuf byteBuf) {
		byteBuf.writeInt(prefix.getBytes().length);
		byteBuf.writeBytes(prefix.getBytes(Charset.forName("UTF-8")));
		byteBuf.writeByte(type.getType());
	}
	
	@Override
	public void decode(ByteBuf byteBuf) {
		int length = byteBuf.readInt();
		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);
		prefix = new String(bytes, Charset.forName("UTF-8"));
		type = ServerType.fromByte(byteBuf.readByte());
	}
	
	
	@AllArgsConstructor
	enum ServerType{
		BUNGEECORD((byte) 0),
		SPIGOT((byte) 1);
		
		@Getter	@Setter private byte type;
		
		public static ServerType fromByte(byte type){
			for(ServerType serverType:values()){
				if(serverType.getType() != type) continue;
				return serverType;
			}
			
			return null;
		}
	}
}
