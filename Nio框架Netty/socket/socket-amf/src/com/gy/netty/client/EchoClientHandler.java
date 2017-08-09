
package com.gy.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.Date;

public class EchoClientHandler extends SimpleChannelInboundHandler<Object[]> {

	private final Object[] msg;

	public EchoClientHandler () {
		short cmd = 100;
		Object[] data = new Object[] {"hello server!!!", new Date()};
		msg = new Object[] {cmd, data};
	}

	@Override
	public void channelActive (ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(msg);
	}

	@Override
	protected void channelRead0 (ChannelHandlerContext ctx, Object[] obj) throws Exception {
		Short cmd = (Short)obj[0];
		Object[] data = (Object[])obj[1];
		System.out.println("server msg \n" + "cmd:" + cmd + "\t data=" + Arrays.toString(data));
	}

}
