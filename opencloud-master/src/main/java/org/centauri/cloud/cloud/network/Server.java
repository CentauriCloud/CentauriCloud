package org.centauri.cloud.cloud.network;

import org.centauri.cloud.cloud.network.handler.PacketDecoder;
import org.centauri.cloud.cloud.network.handler.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.centauri.cloud.cloud.network.util.Pinger;

public class Server {

	public static final boolean EPOLL = Epoll.isAvailable();

	public void run(int port) throws Exception {
		EventLoopGroup loopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		try {
			new ServerBootstrap()
					.group(loopGroup)
					.channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel channel) throws Exception {
							channel.pipeline()
									.addLast(new ReadTimeoutHandler(30))
									.addLast(new PacketDecoder())
									.addLast(new PacketEncoder())
									.addLast(new NetworkHandler());
							new Pinger(channel).start();
						}
					}).bind(port).sync().channel().closeFuture().syncUninterruptibly();

		} finally {
			loopGroup.shutdownGracefully();
		}

	}
}
