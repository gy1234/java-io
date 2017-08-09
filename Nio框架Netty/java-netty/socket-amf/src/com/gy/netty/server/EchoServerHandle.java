
package com.gy.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.Date;

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
	protected void channelRead0 (ChannelHandlerContext ctx, Object[] obj) throws Exception {

		Short cmd = (Short)obj[0];
		Object[] data = (Object[])obj[1];
		System.out.println("client msg \n" + "cmd:" + cmd + "\t data=" + Arrays.toString(data));
		short cmd2 = 100;
		Object[] data2 = new Object[] {"hello client!!!", new Date()};
		Object[] msg = new Object[] {cmd2, data2};
		ctx.writeAndFlush(msg);
		System.out.println("client channelRead0.");
	}
}
