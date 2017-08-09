
package com.gy.netty.server;

import io.netty.channel.ChannelHandlerContext;

import com.gy.constant.ClientCmdConstants;
import com.gy.protobuf.message.ClientPb;
import com.gy.protobuf.message.ClientPb.Request;

public class EchoServerHandle extends io.netty.channel.SimpleChannelInboundHandler<Object[]> {

	private volatile int cnt;

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
		ClientPb.Request request = (Request)msg[1];
		System.out.println("times:" + ++cnt + " client msg \n" + "cmd=" + cmd + ", data=" + request);
		ClientPb.Response.Builder response = ClientPb.Response.newBuilder();
		response.setCmd(request.getCmd() + 10);
		response.setData("hello client!!!");
		byte[] data = response.build().toByteArray();
		ctx.channel().write(new Object[] {ClientCmdConstants.RESPONSE, data});
	}

	@Override
	public void channelReadComplete (ChannelHandlerContext ctx) throws Exception {
		ctx.channel().flush();
	}

}
