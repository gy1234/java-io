
package com.gy.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoServerHandle extends SimpleChannelInboundHandler<Object[]> {

	@Override
	public void channelActive (ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("client channelActive.");
	}

	@Override
	public void channelInactive (ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("client channelInactive.");
	}

	@Override
	public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		System.out.println("client exceptionCaught.");
	}

	@Override
	protected void channelRead0 (ChannelHandlerContext ctx, Object[] msg) throws Exception {
		short cmd = (short)msg[0];
		byte[] data = (byte[])msg[1];
		System.out.println("client msg \n" + "cmd=" + cmd + ", data" + new String(data));
		short cmd2 = ++cmd;
		byte[] data2 = "hello client!!!".getBytes();
		ctx.writeAndFlush(new Object[] {cmd2, data2});
	}
}
