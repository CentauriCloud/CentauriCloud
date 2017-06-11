package org.centauri.cloud.cloud.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import org.centauri.cloud.cloud.network.packets.Packet;
import org.centauri.cloud.cloud.network.packets.PacketServerRegister;

public class PacketDecoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Packet packet = null;
		byte packetId = in.readByte();
		
		if(packetId == 0x01) {
			packet = new PacketServerRegister();
		}
		
		if(packet == null) {
			throw new RuntimeException("Cannot find packet id: " + packetId);
		}
		
		packet.decode(in);
		out.add(packet);
	}
}
