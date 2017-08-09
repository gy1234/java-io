
package com.gy.netty.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.exadel.flamingo.flex.messaging.amf.io.AMF3Deserializer;
import com.exadel.flamingo.flex.messaging.amf.io.AMF3Serializer;

public class AMF3SerializerUtils {

	// amf3序列化对象成二进制字节流
	public static byte[] doSerializer (Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		AMF3Serializer serializer = new AMF3Serializer(out);
		try {
			serializer.writeObject(obj);
			return out.toByteArray();
		} catch (IOException e) {
			System.out.println("amf3 序列错误");
			e.printStackTrace();
		}
		return null;
	}

	// amf3反序列化二进制字节流为对象
	public static <T> T doDeserializer (byte[] content) {
		ByteArrayInputStream in = new ByteArrayInputStream(content);
		AMF3Deserializer deserializer = new AMF3Deserializer(in);
		try {
			return (T)deserializer.readObject();
		} catch (IOException e) {
			System.out.println("amf3 反序列错误");
			e.printStackTrace();
		}
		return null;
	}

	/** @param args */
	public static void main (String[] args) {
		List<Object> list = new ArrayList<>();
		short cmd = 100;
		list.add(cmd);
		Object[] data = new Object[] {1001, "gy1234", 16.8D, new Date()};
		list.add(data);
		Object[] msg = list.toArray();
		Object[] msg2 = doDeserializer(doSerializer(msg));
		System.out.println("cmd=" + msg2[0] + ", data=" + Arrays.toString((Object[])msg2));

	}

}
