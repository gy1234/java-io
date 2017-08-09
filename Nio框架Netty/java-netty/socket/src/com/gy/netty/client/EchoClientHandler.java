
package com.gy.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoClientHandler extends SimpleChannelInboundHandler<Object[]> {

	private final Object[] msg;

	public EchoClientHandler () {
		short cmd = 1;
		byte[] data = "hello server!!!".getBytes();
		msg = new Object[] {cmd, data};
	}

	@Override
	public void channelActive (ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(msg);
	}

	@Override
	protected void channelRead0 (ChannelHandlerContext ctx, Object[] msg) throws Exception {
		short cmd = (short)msg[0];
		byte[] data = (byte[])msg[1];
		System.out.println("server msg \n" + "cmd=" + cmd + ", data=" + new String(data));
	}

}
