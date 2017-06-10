package ch.joel.cloud.cloud.network.handler;

import ch.joel.cloud.cloud.network.packets.InputPacket;
import ch.joel.cloud.cloud.network.packets.Packets;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Class<? extends InputPacket> packetClass = Packets.getIN_PACKETS().get(in.readByte());
		if (packetClass == null) {
			for (int i = 0; i < in.readableBytes(); i++) {
				in.readByte();
			}
			throw new RuntimeException("Packet not registered");
		}
		InputPacket packet = packetClass.newInstance();
		packet.read(in);

	}
}
