package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacketSendPing implements Packet{
	
	long millis;
	
	@Override
	public void encode(ByteBuf buf) {
		
		buf.writeLong(millis);
	}

	@Override
	public void decode(ByteBuf buf) {
		
		millis = buf.readLong();
	}
	
}
