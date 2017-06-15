package org.centauri.cloud.cloud.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.event.events.ServerDenyEvent;
import org.centauri.cloud.common.network.handler.PacketDecoder;
import org.centauri.cloud.common.network.handler.PacketEncoder;
import org.centauri.cloud.common.network.util.Pinger;

import java.net.InetSocketAddress;

public class NettyServer {

	public static final boolean EPOLL = Epoll.isAvailable();
	
	private EventLoopGroup loopGroup;
	
	public void run(int port) throws Exception {
		loopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		try {
			new ServerBootstrap()
					.group(loopGroup)
					.channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel channel) throws Exception {
							if(Cloud.getInstance().isWhitelistActivated()) {
								String hostAddress = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
								if(!Cloud.getInstance().getWhitelistedHosts().contains(hostAddress)) {
									channel.close();
									Cloud.getInstance().getEventManager().callEvent(new ServerDenyEvent(hostAddress, ((InetSocketAddress) channel.remoteAddress()).getPort()));
								}
							}
							
							channel.pipeline()
									.addLast(new ReadTimeoutHandler(Cloud.getInstance().getTimeout()))
									.addLast("splitter", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
									.addLast(new PacketDecoder())
									.addLast("prepender", new LengthFieldPrepender(4))
									.addLast(new PacketEncoder())
									.addLast(new NetworkHandler());
							new Pinger(channel, Cloud.getInstance().getPingerIntervall()).start();
						}
					}).bind(port).sync().channel().closeFuture().syncUninterruptibly();

		} finally {
			loopGroup.shutdownGracefully();
		}

	}
	
	public void stop(){
		loopGroup.shutdownGracefully();
	}
}
