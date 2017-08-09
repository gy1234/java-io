package com.jingtang.config;

import java.util.ResourceBundle;

public class WebConfig {

	private static ResourceBundle bundle = ResourceBundle.getBundle("web-config");

	// 获取amf文件存放url
	public static String loadAmfFileURL() {
		return bundle.getString("amf.file.url");
	}

	// 获取amf文件存后缀名
	public static String loadAmfFileSuffix() {
		return bundle.getString("amf.file.suffix");
	}

	// 获取json文件存放url
	public static String loadJsonFileURL() {
		return bundle.getString("json.file.url");
	}

	// 获取json文件存后缀名
	public static String loadJsonFileSuffix() {
		return bundle.getString("json.file.suffix");
	}


}
