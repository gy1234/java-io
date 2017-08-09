
package com.gy.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.gy.constant.ClientCmdConstants;
import com.gy.protobuf.message.ClientPb;
import com.gy.protobuf.message.ClientPb.Response;

public class EchoClientHandler extends SimpleChannelInboundHandler<Object[]> {

	private volatile int cnt;

	@Override
	public void channelActive (ChannelHandlerContext ctx) throws Exception {
		short cmd= ClientCmdConstants.REQUEST;
		byte[] data=null;
		for (int i = 0; i < 10; i++) {
			data = ClientPb.Request.newBuilder().setCmd(i+1).setData("hello server!!!").build().toByteArray();
			ctx.writeAndFlush(new Object[]{cmd, data});
		}
	}

	@Override
	public void channelInactive (ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

	@Override
	protected void channelRead0 (ChannelHandlerContext ctx, Object[] msg) throws Exception {
		short cmd = (short)msg[0];
		ClientPb.Response response = (Response)msg[1];
		System.out.println("times:" + ++cnt + " server msg \n" + "cmd=" + cmd + ", data=" + response);
	}

}
