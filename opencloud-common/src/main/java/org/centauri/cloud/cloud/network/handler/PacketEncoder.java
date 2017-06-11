package org.centauri.cloud.cloud.network.handler;

import org.centauri.cloud.cloud.network.packets.OutputPacket;
import org.centauri.cloud.cloud.network.packets.Packets;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<OutputPacket> {


	@Override
	protected void encode(ChannelHandlerContext ctx, OutputPacket packet, ByteBuf out) throws Exception {
		int id = Packets.getOUT_PACKETS().indexOf(packet.getClass());
		if (id == -1)
			throw new RuntimeException("Packet not registered: " + packet.getClass().getSimpleName());
		out.writeByte(id);
		packet.write(out);

	}
}
