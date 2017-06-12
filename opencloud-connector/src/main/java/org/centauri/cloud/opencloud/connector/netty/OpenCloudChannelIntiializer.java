package org.centauri.cloud.opencloud.connector.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.network.handler.PacketDecoder;
import org.centauri.cloud.cloud.network.handler.PacketEncoder;

@RequiredArgsConstructor
public class OpenCloudChannelIntiializer extends ChannelInitializer<SocketChannel> {
	
	private final Client client;
	
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		channel.pipeline()
			.addLast(new ReadTimeoutHandler(30))
			.addLast("splitter", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
			.addLast(new PacketDecoder())
			.addLast("prepender", new LengthFieldPrepender(4))
			.addLast(new PacketEncoder())
			.addLast(client.getHandler());
		this.client.setChannel(channel);	
		System.out.println("Netty client started");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Netty client started");
	}
}
