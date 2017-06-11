package org.centauri.cloud.cloud.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface OutputPacket {

	void write(ByteBuf byteBuf) throws IOException;

}
