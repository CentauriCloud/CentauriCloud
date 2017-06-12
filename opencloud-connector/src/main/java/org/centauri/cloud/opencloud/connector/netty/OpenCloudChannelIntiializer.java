package org.centauri.cloud.opencloud.connector.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.network.handler.PacketDecoder;
import org.centauri.cloud.cloud.network.handler.PacketEncoder;

@RequiredArgsConstructor
public class OpenCloudChannelIntiializer extends ChannelInitializer<SocketChannel> {
	
	private final Client client;
	
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		channel.pipeline()
			.addLast(new PacketDecoder())
			.addLast(new PacketEncoder())
			.addLast(client.getHandler());
		this.client.setChannel(channel);		
		System.out.println("Netty client started");
	}
	
}
