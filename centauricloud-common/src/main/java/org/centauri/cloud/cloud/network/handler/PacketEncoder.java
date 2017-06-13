package org.centauri.cloud.cloud.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.centauri.cloud.cloud.network.PacketManager;
import org.centauri.cloud.cloud.network.packets.Packet;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		out.writeByte(PacketManager.getInstance().getId(packet.getClass()));
		packet.encode(out);
	}

}
