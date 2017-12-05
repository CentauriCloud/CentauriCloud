package org.centauri.cloud.common.network.util;

import io.netty.channel.ChannelHandlerContext;
import org.centauri.cloud.common.network.packets.Packet;

public interface PacketHandler {

	void channelRead(ChannelHandlerContext ctx, Packet packet);

}