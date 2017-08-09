
package com.gy.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.gy.netty.codec.ObjectMessageDecoder;
import com.gy.netty.codec.ObjectMessageEncoder;

public class EchoClient {
	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

	/** @param args */
	public static void main (String[] args) {
		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel (SocketChannel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new ObjectMessageDecoder());
						p.addLast(new ObjectMessageEncoder());
						p.addLast(new EchoClientHandler());
					}
				});

			// Start the client.
			try {
				ChannelFuture f = b.connect(HOST, PORT).sync();
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			group.shutdownGracefully();
		}
	}
}
