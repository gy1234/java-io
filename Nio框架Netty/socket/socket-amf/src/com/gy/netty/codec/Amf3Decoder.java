
package com.gy.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.exadel.flamingo.flex.messaging.amf.io.AMF3Deserializer;

/** amf 解码器 */
public class Amf3Decoder extends ByteToMessageDecoder {

	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> objects) throws Exception {
		if (byteBuf.readableBytes() < 4) {
			return;
		}
		// mark读索引
		byteBuf.markReaderIndex();
		int length = byteBuf.readInt();

		if (length < 0) {
			byteBuf.resetReaderIndex();
			ctx.close();
			return;
		}
		if (byteBuf.readableBytes() < length) {
			byteBuf.resetReaderIndex();
			return;
		}
		// 消息body
		byte[] content = new byte[length];
		byteBuf.readBytes(content);
		// amf3反序列化解析
		AMF3Deserializer deserializer = new AMF3Deserializer(new ByteArrayInputStream(content));
		Short cmd = deserializer.readShort();
		Object data = deserializer.readObject();
		Object[] msg = new Object[] {cmd, data};
		objects.add(msg);
	}

}
