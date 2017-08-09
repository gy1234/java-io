package com.jingtang.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.exadel.flamingo.flex.messaging.amf.io.AMF3Deserializer;
import com.exadel.flamingo.flex.messaging.amf.io.AMF3Serializer;
import com.jingtang.config.WebConfig;
import com.jingtang.util.FileUtils;

public class Amf2JsonService {

	private static final String AMF_URL = WebConfig.loadAmfFileURL();
	private static final String AMF_SUFFIX = WebConfig.loadAmfFileSuffix();
	private static final String JSON_URL = WebConfig.loadJsonFileURL();
	private static final String JSON_SUFFIX = WebConfig.loadJsonFileSuffix();

	public String doAnalysis() throws IOException {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			// 模拟数据以amf3序列化数据到文件
			Map<String, Object> data = new HashMap<>();
			data.put("id", 1001);
			data.put("type", 1);
			data.put("name", "张三");
			File file = new File(AMF_URL + "test-amf" + AMF_SUFFIX);
			FileUtils.mkFile(file);
			out = new FileOutputStream(file);
			AMF3Serializer coder = new AMF3Serializer(out);
			coder.writeObject(data);
			out.flush();

			// 模拟amf3反序列化解析文件数据
			in = new FileInputStream(file);
			AMF3Deserializer decoder = new AMF3Deserializer(in);
			data = (Map<String, Object>) decoder.readObject();
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
			// 以json序列化到文件
			byte[] b = JSON.toJSONString(data).getBytes();
			file = new File(JSON_URL + "test-json" + JSON_SUFFIX);
			FileUtils.mkFile(file);
			out = new FileOutputStream(file);
			out.write(b);
			out.flush();
		}
		finally {
			if (null != out) {
				out.close();
			}
			if (null != in) {
				in.close();
			}

		}
		return "success";
	}

}
