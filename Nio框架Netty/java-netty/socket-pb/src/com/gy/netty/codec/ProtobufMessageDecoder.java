
package com.gy.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.gy.protobuf.parser.ClientPbParser;

/** 自定义消息解码器：解决tcp拆包和粘包 */
public class ProtobufMessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 防止tcp粘包和拆包处理, 消息包含消息头和消息体，消息头只有一个表示消息体数据长度的int数据
		if (in.readableBytes() < 6) {
			System.out.println("read byte size < 6");
			return;
		}
		in.markReaderIndex();// mark读索引
		int length = in.readInt();// 消息头包含消息长度
		if (length < 0) {
			in.resetReaderIndex();
			ctx.close();
			return;
		}
		Short cmd = in.readShort();
		if (null == cmd) {
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
		Object cmdData = ClientPbParser.parse(cmd, data);
		Object msg = new Object[]{cmd, cmdData};
		out.add(msg);
	}

}
