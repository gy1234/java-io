
package com.gy.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/** 自定义消息解码器：解决tcp拆包和粘包 */
public class ObjectMessageDecoder extends ByteToMessageDecoder {

	private final static int MIN_SIZE = 6;
	
	
	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 防止tcp粘包和拆包处理, 消息包含消息头和消息体，消息头只有一个表示消息体数据长度的int数据
		if (in.readableBytes() < MIN_SIZE) {
			System.out.println("read byte size < " + MIN_SIZE);
			return;
		}
		in.markReaderIndex();// mark读索引
		int length = in.readInt();// 消息头包含消息长度
		if (length < 0) {
			in.resetReaderIndex();
			ctx.close();
			return;
		}
		Short cmd = in.readShort();// 读取cmd
		if (null == cmd) {
			System.out.println("cmd is null!!!");
			in.resetReaderIndex();
			ctx.close();
			return;
		}
		if (in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}
		byte[] data = new byte[length];// 读取整个消息体
		in.readBytes(data);
		Object[] msg = new Object[]{cmd, data};
		out.add(msg);
	}

}
