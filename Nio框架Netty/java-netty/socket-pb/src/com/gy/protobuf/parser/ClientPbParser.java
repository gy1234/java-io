
package com.gy.protobuf.parser;

import static com.gy.constant.ClientCmdConstants.REQUEST;
import static com.gy.constant.ClientCmdConstants.RESPONSE;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.gy.protobuf.message.ClientPb.Request;
import com.gy.protobuf.message.ClientPb.Response;

// 客户端cmd解析:1-10000
public class ClientPbParser {

	private static final boolean HAS_PARSER;
	static final Map<Short, Message> MESSAGELITE = new HashMap<Short, Message>();
	// 注册客户端cmd对应的pb类描述信息
	static {
		boolean hasParser = false;
		try {
			// MessageLite.getParsetForType() is not available until protobuf 2.5.0.
			MessageLite.class.getDeclaredMethod("getParserForType");
			hasParser = true;
		} catch (Throwable t) {
			// Ignore
		}
		HAS_PARSER = hasParser;
		register();
	}

	// 注册指令相关的pb解析信息
	private static void register () {
		MESSAGELITE.put(REQUEST, Request.getDefaultInstance());
		MESSAGELITE.put(RESPONSE, Response.getDefaultInstance());
	}

	// 根据cmd获取对应pb类messagelite
	private static Message getterMessageByCmd (short cmd) {
		return MESSAGELITE.get(cmd);
	}

	/** @description 根据action cmd和序列化的字节数据反序列化解析
	 * @return 具体的message
	 * @param cmd
	 * @param data
	 * @return */
	public static Object parse (short cmd, byte[] data) {
		Message message = getterMessageByCmd(cmd);
		if (null != message) {
			try {
				if (HAS_PARSER) {
					return message.getParserForType().parseFrom(data);
				} else {
					return message.newBuilderForType().mergeFrom(data).build();
				}
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// use test
	public static void main (String[] args) {
		Request req = Request.newBuilder().setCmd(1).setData("auto generate pb !!!").build();
		System.out.println(req);
		// 模拟服务器测试反序列解析pb
		short cmd = (short)req.getCmd();
		byte[] data = req.toByteArray();
		// 具体action处理得到自己的pb实体类
		Request req2 = (Request)parse(cmd, data);
		System.out.println(req2);
	}
}
