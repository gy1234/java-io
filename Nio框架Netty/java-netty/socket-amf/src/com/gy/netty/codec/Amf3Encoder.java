
package com.gy.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

import com.exadel.flamingo.flex.messaging.amf.io.AMF3Serializer;

/** amf 编码器 */
public class Amf3Encoder extends MessageToByteEncoder<Object[]> {

	@Override
	protected void encode (ChannelHandlerContext ctx, Object[] msg, ByteBuf byteBuf) throws Exception {
		// amf3编码
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		AMF3Serializer serializer = new AMF3Serializer(out);
		serializer.writeShort((Short)msg[0]);
		serializer.writeObject(msg[1]);
		byte[] b = out.toByteArray();
		byteBuf.writeInt(b.length);
		byteBuf.writeBytes(b);
	}

}
