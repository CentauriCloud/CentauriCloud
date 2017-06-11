package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface InputPacket {

	void read(ByteBuf byteBuf) throws IOException;

}
