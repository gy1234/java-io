
package com.gy.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/** 自定义消息编码器：解决tcp拆包和粘包 */
public class ProtobufMessageEncoder extends MessageToByteEncoder<Object[]> {

	@Override
	protected void encode (ChannelHandlerContext ctx, Object[] msg, ByteBuf out) throws Exception {
		short cmd = (short) msg[0];
		byte[] data = (byte[])msg[1];
		out.writeInt(data.length);
		out.writeShort(cmd);
		out.writeBytes(data);
	}

}
