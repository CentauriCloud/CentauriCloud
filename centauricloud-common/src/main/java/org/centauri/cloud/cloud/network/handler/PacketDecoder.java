package org.centauri.cloud.cloud.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import org.centauri.cloud.cloud.network.PacketManager;
import org.centauri.cloud.cloud.network.packets.Packet;

public class PacketDecoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes() <= 0)
			return;
		
		Packet packet = null;
		byte packetId = in.readByte();
		
		Class<? extends Packet> clazz = PacketManager.getInstance().getPacketClass(packetId);
		
		if(clazz != null)
			packet = clazz.newInstance();
		
		if(packet == null) {
			throw new RuntimeException("Cannot find packet id: " + packetId);
		}
		
		packet.decode(in);
		out.add(packet);
	}
}
