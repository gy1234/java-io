
package com.gy.netty.server;

import com.gy.netty.codec.ProtobufMessageDecoder;
import com.gy.netty.codec.ProtobufMessageEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
	static final int PORT = Integer.parseInt(System.getProperty("port", "7007"));

	/** @param args */
	public static void main (String[] args) {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workerGroup);
			server.channel(NioServerSocketChannel.class);
			server.option(ChannelOption.SO_BACKLOG, 100);
			server.handler(new LoggingHandler(io.netty.handler.logging.LogLevel.INFO));
			server.childHandler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
				@Override
				protected void initChannel (SocketChannel ch) throws Exception {
					io.netty.channel.ChannelPipeline p = ch.pipeline();
					p.addLast(new ProtobufMessageDecoder());
					p.addLast(new ProtobufMessageEncoder());
					p.addLast(new EchoServerHandle());
				}

			});

			// Start the server.
			try {
				ChannelFuture f = server.bind(PORT).sync();

				System.out.println("echo server start success on port: " + PORT);
				// Wait until the server socket is closed.
				f.channel().closeFuture().sync();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			// Start the server.
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
